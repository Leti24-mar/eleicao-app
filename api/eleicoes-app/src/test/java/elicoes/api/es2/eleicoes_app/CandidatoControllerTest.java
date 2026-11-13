package elicoes.api.es2.eleicoes_app;

import elicoes.api.es2.eleicoes_app.domain.Candidato;
import elicoes.api.es2.eleicoes_app.domain.Eleicao;
import elicoes.api.es2.eleicoes_app.domain.Enum.CargoUsuario;
import elicoes.api.es2.eleicoes_app.domain.Enum.StatusEleicao;
import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;
import elicoes.api.es2.eleicoes_app.domain.Usuario;
import elicoes.api.es2.eleicoes_app.domain.Voto;
import elicoes.api.es2.eleicoes_app.dto.CandidatoRequest;
import elicoes.api.es2.eleicoes_app.repository.CandidatoRepository;
import elicoes.api.es2.eleicoes_app.repository.EleicaoRepository;
import elicoes.api.es2.eleicoes_app.repository.UsuarioRepository;
import elicoes.api.es2.eleicoes_app.repository.VotoRepository;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CandidatoControllerTest {

    private static final String CPF_CHEFE = "39053344705";
    private static final String CPF_CANDIDATO = "52998224725";
    private static final String CPF_OUTRO = "15350946056";
    private static final String CPF_ELEITOR = "11144477735";
    private static final String SENHA_CHEFE = "SenhaForte1";
    private static final String SENHA_CANDIDATO = "SenhaCandidato1";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EleicaoRepository eleicaoRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @Autowired
    private VotoRepository votoRepository;

    private Long eleicaoId;

    @BeforeEach
    void preparar() {
        votoRepository.deleteAll();
        candidatoRepository.deleteAll();
        usuarioRepository.deleteAll();
        eleicaoRepository.deleteAll();

        Eleicao eleicao = new Eleicao();
        eleicao.setData(LocalDate.of(2026, 10, 4));
        eleicao.setTurno(1);
        eleicao.setHorarioAbertura(LocalTime.of(8, 0));
        eleicao.setHorarioEncerramento(LocalTime.of(17, 0));
        eleicao.setStatus(StatusEleicao.AGENDADA);
        eleicaoId = eleicaoRepository.save(eleicao).getId();

        salvarUsuario("Chefe", "Secao", CPF_CHEFE, "chefe@example.com", SENHA_CHEFE, CargoUsuario.CHEFE_SECAO);
    }

    @Test
    void recusaChamadaSemAutenticacao() throws Exception {
        mockMvc.perform(get("/candidatos"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Não autenticado"));
    }

    @Test
    void recusaEleitorNoGerenciamento() throws Exception {
        salvarUsuario("Eleitor", "Comum", CPF_ELEITOR, "eleitor@example.com", "SenhaEleitor1", CargoUsuario.ELEITOR);

        mockMvc.perform(get("/candidatos").with(httpBasic(CPF_ELEITOR, "SenhaEleitor1")))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.mensagem").value("Acesso negado"));
    }

    @Test
    void cadastraConsultaAlteraEExcluiSemExporSenha() throws Exception {
        String corpo = mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pedido(CPF_CANDIDATO, "ana@example.com", "123", SENHA_CANDIDATO))
                                .replace("}", ",\"cargo\":\"CHEFE_SECAO\"}")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.senha").doesNotExist())
                .andExpect(jsonPath("$.tipo").value("SENADOR"))
                .andExpect(jsonPath("$.email").value("ana@example.com"))
                .andExpect(jsonPath("$.eleicaoId").value(eleicaoId))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(corpo).doesNotContain(SENHA_CANDIDATO);
        Usuario usuario = usuarioRepository.findByCpf(CPF_CANDIDATO).orElseThrow();
        assertThat(usuario.getCargo()).isEqualTo(CargoUsuario.CANDIDATO);
        assertThat(usuario.getSenha()).startsWith("$2a$12$").doesNotContain(SENHA_CANDIDATO);
        assertThat(passwordEncoder.matches(SENHA_CANDIDATO, usuario.getSenha())).isTrue();

        Long id = candidatoRepository.findAll().getFirst().getId();

        mockMvc.perform(get("/candidatos/{id}", id).with(httpBasic(CPF_CHEFE, SENHA_CHEFE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Ana"))
                .andExpect(jsonPath("$.senha").doesNotExist());

        String hashAnterior = usuario.getSenha();
        mockMvc.perform(put("/candidatos/{id}", id)
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new elicoes.api.es2.eleicoes_app.dto.CandidatoAtualizacaoRequest(
                                "Ana", "Souza", "Maria Souza", CPF_CANDIDATO, "11987654321",
                                "ana@example.com", null, eleicaoId, "123", "Partido", TipoCandidato.SENADOR))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sobrenome").value("Souza"))
                .andExpect(jsonPath("$.senha").doesNotExist());

        assertThat(usuarioRepository.findByCpf(CPF_CANDIDATO).orElseThrow().getSenha()).isEqualTo(hashAnterior);

        mockMvc.perform(delete("/candidatos/{id}", id).with(httpBasic(CPF_CHEFE, SENHA_CHEFE)))
                .andExpect(status().isNoContent());

        assertThat(candidatoRepository.findById(id)).isEmpty();
        assertThat(usuarioRepository.findByCpf(CPF_CANDIDATO)).isEmpty();
    }

    @Test
    void rejeitaDadosInvalidosSemDevolverASenha() throws Exception {
        String senhaInvalida = "curta";
        String corpo = mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pedido(CPF_CANDIDATO, "ana@example.com", "12", senhaInvalida))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(corpo).doesNotContain(senhaInvalida);
        assertThat(usuarioRepository.findByCpf(CPF_CANDIDATO)).isEmpty();
    }

    @Test
    void rejeitaCpfDuplicadoNumeroDuplicadoEEleicaoInexistente() throws Exception {
        mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pedido(CPF_CANDIDATO, "ana@example.com", "123", SENHA_CANDIDATO))))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pedido(CPF_CANDIDATO, "outra@example.com", "456", SENHA_CANDIDATO))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem").value("CPF já cadastrado"));

        mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pedido(CPF_OUTRO, "outro@example.com", "123", SENHA_CANDIDATO))))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem").value("Número já utilizado nesta eleição"));

        mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(new CandidatoRequest(
                                "Ana", "Silva", "Maria Souza", CPF_OUTRO, "11987654321",
                                "outro@example.com", SENHA_CANDIDATO, 9_999_999L, "456", "Partido",
                                TipoCandidato.SENADOR))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Eleição não encontrada"));
    }

    @Test
    void limitaPaginaERecusaOrdenacaoPorCampoSensivel() throws Exception {
        mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pedido(CPF_CANDIDATO, "ana@example.com", "123", SENHA_CANDIDATO))))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/candidatos")
                        .queryParam("size", "1000")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Tamanho de página acima do limite"));

        String listagem = mockMvc.perform(get("/candidatos").with(httpBasic(CPF_CHEFE, SENHA_CHEFE)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(listagem).doesNotContain(SENHA_CANDIDATO);

        mockMvc.perform(get("/candidatos")
                        .queryParam("sort", "usuario.senha")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Ordenação inválida"));
    }

    @Test
    void preservaCandidatoComVotoRegistrado() throws Exception {
        mockMvc.perform(post("/candidatos")
                        .with(httpBasic(CPF_CHEFE, SENHA_CHEFE))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(pedido(CPF_CANDIDATO, "ana@example.com", "123", SENHA_CANDIDATO))))
                .andExpect(status().isCreated());

        Candidato candidato = candidatoRepository.findAll().getFirst();
        Voto voto = new Voto();
        voto.setCandidato(candidato);
        voto.setEleicao(eleicaoRepository.findById(eleicaoId).orElseThrow());
        votoRepository.save(voto);

        mockMvc.perform(delete("/candidatos/{id}", candidato.getId()).with(httpBasic(CPF_CHEFE, SENHA_CHEFE)))
                .andExpect(status().isConflict());

        assertThat(candidatoRepository.findById(candidato.getId())).isPresent();
    }

    @Test
    void rejeitaIdentificadorInvalido() throws Exception {
        mockMvc.perform(get("/candidatos/{id}", 0).with(httpBasic(CPF_CHEFE, SENHA_CHEFE)))
                .andExpect(status().isBadRequest());
    }

    private CandidatoRequest pedido(String cpf, String email, String numero, String senha) {
        return new CandidatoRequest(
                "Ana", "Silva", "Maria Souza", cpf, "11987654321", email, senha, eleicaoId, numero, "Partido",
                TipoCandidato.SENADOR);
    }

    private String json(CandidatoRequest request) throws Exception {
        return objectMapper.writeValueAsString(request);
    }

    private void salvarUsuario(String nome, String sobrenome, String cpf, String email, String senha, CargoUsuario cargo) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setNomeDaMae("Mae do Usuario");
        usuario.setCpf(cpf);
        usuario.setTelefone("11999999999");
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setCargo(cargo);
        usuarioRepository.save(usuario);
    }

}
