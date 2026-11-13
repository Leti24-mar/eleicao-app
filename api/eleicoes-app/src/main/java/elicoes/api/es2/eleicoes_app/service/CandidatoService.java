package elicoes.api.es2.eleicoes_app.service;

import elicoes.api.es2.eleicoes_app.domain.Candidato;
import elicoes.api.es2.eleicoes_app.domain.Eleicao;
import elicoes.api.es2.eleicoes_app.domain.Enum.CargoUsuario;
import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;
import elicoes.api.es2.eleicoes_app.domain.Usuario;
import elicoes.api.es2.eleicoes_app.dto.CandidatoAtualizacaoRequest;
import elicoes.api.es2.eleicoes_app.dto.CandidatoRequest;
import elicoes.api.es2.eleicoes_app.dto.CandidatoResponse;
import elicoes.api.es2.eleicoes_app.exception.ConflitoDeDadosException;
import elicoes.api.es2.eleicoes_app.exception.RecursoNaoEncontradoException;
import elicoes.api.es2.eleicoes_app.exception.RequisicaoInvalidaException;
import elicoes.api.es2.eleicoes_app.repository.CandidatoRepository;
import elicoes.api.es2.eleicoes_app.repository.EleicaoRepository;
import elicoes.api.es2.eleicoes_app.repository.UsuarioRepository;
import elicoes.api.es2.eleicoes_app.repository.VotoRepository;
import elicoes.api.es2.eleicoes_app.validation.RegrasNumeroCandidato;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
public class CandidatoService {

    private static final int TAMANHO_MAXIMO_PAGINA = 50;
    private static final Set<String> CAMPOS_ORDENACAO = Set.of("id", "numero", "partido", "tipo");

    private final CandidatoRepository candidatoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EleicaoRepository eleicaoRepository;
    private final VotoRepository votoRepository;
    private final PasswordEncoder passwordEncoder;

    public CandidatoService(CandidatoRepository candidatoRepository,
                            UsuarioRepository usuarioRepository,
                            EleicaoRepository eleicaoRepository,
                            VotoRepository votoRepository,
                            PasswordEncoder passwordEncoder) {
        this.candidatoRepository = candidatoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eleicaoRepository = eleicaoRepository;
        this.votoRepository = votoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('CHEFE_SECAO')")
    public CandidatoResponse cadastrar(CandidatoRequest request) {
        validarSenha(request.senha(), request.cpf(), request.email());
        validarNumero(request.tipo(), request.numero());
        garantirCpfDisponivel(request.cpf(), null);
        garantirEmailDisponivel(request.email(), null);

        Eleicao eleicao = buscarEleicao(request.eleicaoId());
        garantirNumeroDisponivel(eleicao.getId(), request.numero(), null);

        Usuario usuario = new Usuario();
        preencherUsuario(usuario, request.nome(), request.sobrenome(), request.nomeDaMae(), request.cpf(),
                request.telefone(), request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario = usuarioRepository.save(usuario);

        Candidato candidato = new Candidato();
        candidato.setUsuario(usuario);
        candidato.setEleicao(eleicao);
        candidato.setNumero(request.numero());
        candidato.setPartido(request.partido());
        candidato.setTipo(request.tipo());
        candidato = candidatoRepository.save(candidato);

        log.info("Candidato cadastrado id={} eleicaoId={}", candidato.getId(), eleicao.getId());
        return toResponse(candidato);
    }

    @PreAuthorize("hasRole('CHEFE_SECAO')")
    public CandidatoResponse alterar(Long id, CandidatoAtualizacaoRequest request) {
        Candidato candidato = buscar(id);
        Usuario usuario = candidato.getUsuario();
        validarNumero(request.tipo(), request.numero());
        garantirCpfDisponivel(request.cpf(), usuario.getId());
        garantirEmailDisponivel(request.email(), usuario.getId());

        Eleicao eleicao = buscarEleicao(request.eleicaoId());
        garantirNumeroDisponivel(eleicao.getId(), request.numero(), candidato.getId());
        if (candidatoRepository.existsByUsuarioIdAndEleicaoIdAndIdNot(usuario.getId(), eleicao.getId(), candidato.getId())) {
            throw new ConflitoDeDadosException("Usuário já é candidato nesta eleição");
        }

        preencherUsuario(usuario, request.nome(), request.sobrenome(), request.nomeDaMae(), request.cpf(),
                request.telefone(), request.email());
        if (request.senha() != null) {
            validarSenha(request.senha(), request.cpf(), request.email());
            usuario.setSenha(passwordEncoder.encode(request.senha()));
        }
        candidato.setEleicao(eleicao);
        candidato.setNumero(request.numero());
        candidato.setPartido(request.partido());
        candidato.setTipo(request.tipo());

        log.info("Candidato alterado id={} eleicaoId={}", candidato.getId(), eleicao.getId());
        return toResponse(candidato);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('CHEFE_SECAO')")
    public Page<CandidatoResponse> listar(Pageable pageable) {
        return candidatoRepository.findAll(paginaSegura(pageable)).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('CHEFE_SECAO')")
    public CandidatoResponse consultar(Long id) {
        return toResponse(buscar(id));
    }

    @PreAuthorize("hasRole('CHEFE_SECAO')")
    public void excluir(Long id) {
        Candidato candidato = buscar(id);
        if (votoRepository.existsByCandidatoId(id)) {
            throw new ConflitoDeDadosException("Candidato com votos registrados não pode ser excluído");
        }
        Long usuarioId = candidato.getUsuario().getId();
        candidatoRepository.delete(candidato);
        candidatoRepository.flush();
        if (!candidatoRepository.existsByUsuarioId(usuarioId)) {
            usuarioRepository.deleteById(usuarioId);
        }
        log.info("Candidato excluído id={}", id);
    }

    private Candidato buscar(Long id) {
        return candidatoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Candidato não encontrado"));
    }

    private Eleicao buscarEleicao(Long id) {
        return eleicaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Eleição não encontrada"));
    }

    private void preencherUsuario(Usuario usuario, String nome, String sobrenome, String nomeDaMae, String cpf,
                                  String telefone, String email) {
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setNomeDaMae(nomeDaMae);
        usuario.setCpf(cpf);
        usuario.setTelefone(telefone);
        usuario.setEmail(email);
        usuario.setCargo(CargoUsuario.CANDIDATO);
    }

    private void validarSenha(String senha, String cpf, String email) {
        if (senha.equals(cpf) || senha.equalsIgnoreCase(email)) {
            throw new RequisicaoInvalidaException("A senha não pode ser igual ao CPF ou ao e-mail");
        }
    }

    private void validarNumero(TipoCandidato tipo, String numero) {
        if (!RegrasNumeroCandidato.compativel(tipo, numero)) {
            throw new RequisicaoInvalidaException("Número incompatível com o cargo");
        }
    }

    private void garantirCpfDisponivel(String cpf, Long usuarioId) {
        boolean emUso = usuarioId == null
                ? usuarioRepository.existsByCpf(cpf)
                : usuarioRepository.existsByCpfAndIdNot(cpf, usuarioId);
        if (emUso) {
            throw new ConflitoDeDadosException("CPF já cadastrado");
        }
    }

    private void garantirEmailDisponivel(String email, Long usuarioId) {
        boolean emUso = usuarioId == null
                ? usuarioRepository.existsByEmailIgnoreCase(email)
                : usuarioRepository.existsByEmailIgnoreCaseAndIdNot(email, usuarioId);
        if (emUso) {
            throw new ConflitoDeDadosException("E-mail já cadastrado");
        }
    }

    private void garantirNumeroDisponivel(Long eleicaoId, String numero, Long candidatoId) {
        boolean emUso = candidatoId == null
                ? candidatoRepository.existsByEleicaoIdAndNumero(eleicaoId, numero)
                : candidatoRepository.existsByEleicaoIdAndNumeroAndIdNot(eleicaoId, numero, candidatoId);
        if (emUso) {
            throw new ConflitoDeDadosException("Número já utilizado nesta eleição");
        }
    }

    private Pageable paginaSegura(Pageable pageable) {
        if (pageable.getPageSize() > TAMANHO_MAXIMO_PAGINA) {
            throw new RequisicaoInvalidaException("Tamanho de página acima do limite");
        }
        Sort ordenacao = pageable.getSort().isUnsorted() ? Sort.by("id") : pageable.getSort();
        for (Sort.Order ordem : ordenacao) {
            if (!CAMPOS_ORDENACAO.contains(ordem.getProperty())) {
                throw new RequisicaoInvalidaException("Ordenação inválida");
            }
        }
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), ordenacao);
    }

    private CandidatoResponse toResponse(Candidato candidato) {
        Usuario usuario = candidato.getUsuario();
        return new CandidatoResponse(
                candidato.getId(),
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getNomeDaMae(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getEmail(),
                candidato.getEleicao().getId(),
                candidato.getNumero(),
                candidato.getPartido(),
                candidato.getTipo()
        );
    }

}
