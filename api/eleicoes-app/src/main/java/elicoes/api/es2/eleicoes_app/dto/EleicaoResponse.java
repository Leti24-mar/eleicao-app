package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.StatusEleicao;

import java.time.LocalDate;
import java.time.LocalTime;

public record EleicaoResponse(
        Long id,
        LocalDate data,
        Integer turno,
        LocalTime horarioAbertura,
        LocalTime horarioEncerramento,
        StatusEleicao status
) {
}
