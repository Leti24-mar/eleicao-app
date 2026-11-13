package elicoes.api.es2.eleicoes_app.controller;

import elicoes.api.es2.eleicoes_app.dto.ResultadoCargoResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/eleicoes/{eleicaoId}/resultado")
public class ResultadoController {

    @GetMapping
    public List<ResultadoCargoResponse> consultar(@PathVariable Long eleicaoId) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}
