package elicoes.api.es2.eleicoes_app.validation;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

public interface DadosNumeroCandidato {

    TipoCandidato tipo();

    String numero();

}
