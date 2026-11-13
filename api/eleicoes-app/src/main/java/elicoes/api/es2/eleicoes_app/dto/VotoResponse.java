package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

public record VotoResponse(Long eleicaoId, Long candidatoId, TipoCandidato tipo, boolean confirmado) {
}
