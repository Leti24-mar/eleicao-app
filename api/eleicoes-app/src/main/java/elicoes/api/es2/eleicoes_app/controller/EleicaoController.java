package elicoes.api.es2.eleicoes_app.controller;

import elicoes.api.es2.eleicoes_app.domain.Enum.TipoCandidato;
import elicoes.api.es2.eleicoes_app.dto.CandidatoResumoResponse;
import elicoes.api.es2.eleicoes_app.dto.EleicaoRequest;
import elicoes.api.es2.eleicoes_app.dto.EleicaoResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/eleicoes")
public class EleicaoController {

    @PostMapping
    public EleicaoResponse cadastrar(@RequestBody EleicaoRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping
    public List<EleicaoResponse> listar() {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}")
    public EleicaoResponse consultar(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/{id}/abertura")
    public EleicaoResponse abrir(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/{id}/encerramento")
    public EleicaoResponse encerrar(@PathVariable Long id) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @GetMapping("/{id}/candidatos")
    public List<CandidatoResumoResponse> listarCandidatos(@PathVariable Long id,
                                                          @RequestParam(required = false) TipoCandidato tipo) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}
