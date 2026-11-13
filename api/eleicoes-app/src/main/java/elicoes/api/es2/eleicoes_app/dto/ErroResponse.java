package elicoes.api.es2.eleicoes_app.dto;

import java.util.List;

public record ErroResponse(String mensagem, List<CampoErro> erros) {

    public ErroResponse(String mensagem) {
        this(mensagem, List.of());
    }

}
