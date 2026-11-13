package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

public record ApuracaoCandidatoResponse(
        Long candidatoId,
        String nome,
        String numero,
        String partido,
        TipoCandidato tipo,
        long quantidadeVotos
) {
}
