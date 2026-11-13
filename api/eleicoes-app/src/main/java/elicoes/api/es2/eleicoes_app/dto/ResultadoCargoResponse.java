package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

import java.util.List;

public record ResultadoCargoResponse(
        TipoCandidato tipo,
        Long candidatoMaisVotadoId,
        List<ApuracaoCandidatoResponse> candidatos
) {
}
