package elicoes.api.es2.eleicoes_app.validation;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;

public final class RegrasNumeroCandidato {

    private RegrasNumeroCandidato() {
    }

    public static int tamanho(TipoCandidato tipo) {
        return switch (tipo) {
            case PRESIDENTE_DA_REPUBLICA, GOVERNADOR -> 2;
            case SENADOR -> 3;
            case DEPUTADO_FEDERAL -> 4;
            case DEPUTADO_ESTADUAL -> 5;
        };
    }

    public static boolean compativel(TipoCandidato tipo, String numero) {
        if (tipo == null || numero == null) {
            return false;
        }
        return numero.matches("\\d{" + tamanho(tipo) + "}");
    }

}
