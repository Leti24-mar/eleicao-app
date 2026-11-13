package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.CargoUsuario;

public record EleitorResponse(
        Long id,
        String nome,
        String sobrenome,
        String nomeDaMae,
        String cpf,
        String telefone,
        String email,
        CargoUsuario cargo
) {
}
