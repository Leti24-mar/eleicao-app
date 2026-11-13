package elicoes.api.es2.eleicoes_app.dto;

public record EleitorRequest(
        String nome,
        String sobrenome,
        String nomeDaMae,
        String cpf,
        String telefone,
        String email,
        String senha
) {
}
