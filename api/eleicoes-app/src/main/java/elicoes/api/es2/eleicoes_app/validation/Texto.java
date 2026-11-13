package elicoes.api.es2.eleicoes_app.validation;

import java.util.Locale;

public final class Texto {

    private Texto() {
    }

    public static String limpar(String valor) {
        if (valor == null) {
            return null;
        }
        return valor.trim();
    }

    public static String email(String valor) {
        String limpo = limpar(valor);
        if (limpo == null) {
            return null;
        }
        return limpo.toLowerCase(Locale.ROOT);
    }

    public static String digitos(String valor) {
        if (valor == null) {
            return null;
        }
        return valor.replaceAll("\\D", "");
    }

    public static String senha(String valor) {
        if (valor == null) {
            return null;
        }
        String limpa = valor.trim();
        return limpa.isEmpty() ? null : limpa;
    }

}
