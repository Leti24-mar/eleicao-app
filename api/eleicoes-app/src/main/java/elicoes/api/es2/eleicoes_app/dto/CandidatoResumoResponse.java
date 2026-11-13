package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

public record CandidatoResumoResponse(
        Long id,
        String nome,
        String numero,
        String partido,
        TipoCandidato tipo
) {
}
