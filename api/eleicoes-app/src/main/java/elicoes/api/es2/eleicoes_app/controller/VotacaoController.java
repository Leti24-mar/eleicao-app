package elicoes.api.es2.eleicoes_app.controller;

import elicoes.api.es2.eleicoes_app.dto.VotoRequest;
import elicoes.api.es2.eleicoes_app.dto.VotoResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/eleicoes/{eleicaoId}/votos")
public class VotacaoController {

    @PostMapping
    public VotoResponse registrar(@PathVariable Long eleicaoId, @RequestBody VotoRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/confirmacao")
    public VotoResponse confirmar(@PathVariable Long eleicaoId, @RequestBody VotoRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}
