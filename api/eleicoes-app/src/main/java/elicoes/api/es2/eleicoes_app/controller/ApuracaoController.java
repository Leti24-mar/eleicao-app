package elicoes.api.es2.eleicoes_app.controller;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;
import elicoes.api.es2.eleicoes_app.dto.ApuracaoCandidatoResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/eleicoes/{eleicaoId}/apuracao")
public class ApuracaoController {

    @GetMapping
    public List<ApuracaoCandidatoResponse> consultar(@PathVariable Long eleicaoId,
                                                     @RequestParam(required = false) TipoCandidato tipo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}
