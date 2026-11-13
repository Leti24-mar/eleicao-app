package elicoes.api.es2.eleicoes_app.dto;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

public record CandidatoResponse(
        Long id,
        Long usuarioId,
        String nome,
        String sobrenome,
        String nomeDaMae,
        String cpf,
        String telefone,
        String email,
        Long eleicaoId,
        String numero,
        String partido,
        TipoCandidato tipo
) {
}
