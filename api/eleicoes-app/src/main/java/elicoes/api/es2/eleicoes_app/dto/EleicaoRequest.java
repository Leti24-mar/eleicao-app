package elicoes.api.es2.eleicoes_app.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EleicaoRequest(
        LocalDate data,
        Integer turno,
        LocalTime horarioAbertura,
        LocalTime horarioEncerramento
) {
}
