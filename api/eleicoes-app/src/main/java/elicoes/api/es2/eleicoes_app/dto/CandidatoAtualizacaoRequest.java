package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;
import elicoes.api.es2.eleicoes_app.validation.Cpf;
import elicoes.api.es2.eleicoes_app.validation.CpfUtils;
import elicoes.api.es2.eleicoes_app.validation.DadosNumeroCandidato;
import elicoes.api.es2.eleicoes_app.validation.NumeroCompativel;
import elicoes.api.es2.eleicoes_app.validation.Texto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@NumeroCompativel
public record CandidatoAtualizacaoRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 2, max = 80, message = "Nome deve ter entre 2 e 80 caracteres")
        @Pattern(regexp = "^[\\p{L}][\\p{L} '\\-]{1,79}$", message = "Nome contém caracteres inválidos")
        String nome,
        @NotBlank(message = "Sobrenome é obrigatório")
        @Size(min = 2, max = 80, message = "Sobrenome deve ter entre 2 e 80 caracteres")
        @Pattern(regexp = "^[\\p{L}][\\p{L} '\\-]{1,79}$", message = "Sobrenome contém caracteres inválidos")
        String sobrenome,
        @NotBlank(message = "Nome da mãe é obrigatório")
        @Size(min = 2, max = 80, message = "Nome da mãe deve ter entre 2 e 80 caracteres")
        @Pattern(regexp = "^[\\p{L}][\\p{L} '\\-]{1,79}$", message = "Nome da mãe contém caracteres inválidos")
        String nomeDaMae,
        @NotBlank(message = "CPF é obrigatório")
        @Cpf
        String cpf,
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos")
        String telefone,
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 120, message = "E-mail deve ter no máximo 120 caracteres")
        String email,
        @Size(min = 8, max = 72, message = "Senha deve ter entre 8 e 72 caracteres")
        String senha,
        @NotNull(message = "Eleição é obrigatória")
        @Positive(message = "Eleição inválida")
        Long eleicaoId,
        @NotBlank(message = "Número é obrigatório")
        @Pattern(regexp = "\\d{2,5}", message = "Número deve conter apenas dígitos")
        String numero,
        @NotBlank(message = "Partido é obrigatório")
        @Size(min = 2, max = 80, message = "Partido deve ter entre 2 e 80 caracteres")
        @Pattern(regexp = "^[\\p{L}][\\p{L} '\\-]{1,79}$", message = "Partido contém caracteres inválidos")
        String partido,
        @NotNull(message = "Cargo é obrigatório")
        TipoCandidato tipo
) implements DadosNumeroCandidato {

    public CandidatoAtualizacaoRequest {
        nome = Texto.limpar(nome);
        sobrenome = Texto.limpar(sobrenome);
        nomeDaMae = Texto.limpar(nomeDaMae);
        cpf = CpfUtils.somenteDigitos(cpf);
        telefone = Texto.digitos(telefone);
        email = Texto.email(email);
        senha = Texto.senha(senha);
        numero = Texto.limpar(numero);
        partido = Texto.limpar(partido);
    }

}
