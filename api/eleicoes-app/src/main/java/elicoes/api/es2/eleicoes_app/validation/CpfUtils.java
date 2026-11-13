package elicoes.api.es2.eleicoes_app.validation;

public final class CpfUtils {

    private CpfUtils() {
    }

    public static String somenteDigitos(String cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.replaceAll("\\D", "");
    }

    public static boolean valido(String cpf) {
        String digitos = somenteDigitos(cpf);
        if (digitos == null || !digitos.matches("\\d{11}")) {
            return false;
        }
        if (digitos.chars().distinct().count() == 1) {
            return false;
        }
        return digito(digitos, 9) == digitos.charAt(9) && digito(digitos, 10) == digitos.charAt(10);
    }

    private static char digito(String digitos, int tamanho) {
        int soma = 0;
        int peso = tamanho + 1;
        for (int indice = 0; indice < tamanho; indice++) {
            soma += (digitos.charAt(indice) - '0') * peso--;
        }
        int resto = soma % 11;
        int valor = resto < 2 ? 0 : 11 - resto;
        return (char) ('0' + valor);
    }

}
