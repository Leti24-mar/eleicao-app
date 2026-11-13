package elicoes.api.es2.eleicoes_app.controller;

import elicoes.api.es2.eleicoes_app.dto.CandidatoAtualizacaoRequest;
import elicoes.api.es2.eleicoes_app.dto.CandidatoRequest;
import elicoes.api.es2.eleicoes_app.dto.CandidatoResponse;
import elicoes.api.es2.eleicoes_app.service.CandidatoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidatos")
@Validated
public class CandidatoController {

    private final CandidatoService candidatoService;

    public CandidatoController(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidatoResponse cadastrar(@Valid @RequestBody CandidatoRequest request) {
        return candidatoService.cadastrar(request);
    }

    @PutMapping("/{id}")
    public CandidatoResponse alterar(@PathVariable @Positive(message = "Identificador inválido") Long id,
                                     @Valid @RequestBody CandidatoAtualizacaoRequest request) {
        return candidatoService.alterar(id, request);
    }

    @GetMapping
    public Page<CandidatoResponse> listar(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return candidatoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public CandidatoResponse consultar(@PathVariable @Positive(message = "Identificador inválido") Long id) {
        return candidatoService.consultar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @Positive(message = "Identificador inválido") Long id) {
        candidatoService.excluir(id);
    }

}
