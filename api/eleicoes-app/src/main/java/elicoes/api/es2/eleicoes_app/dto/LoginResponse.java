package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.CargoUsuario;

public record LoginResponse(Long id, String nome, CargoUsuario cargo) {
}
