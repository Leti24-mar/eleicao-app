package elicoes.api.es2.eleicoes_app.config;

import elicoes.api.es2.eleicoes_app.dto.CampoErro;
import elicoes.api.es2.eleicoes_app.dto.ErroResponse;
import elicoes.api.es2.eleicoes_app.exception.ConflitoDeDadosException;
import elicoes.api.es2.eleicoes_app.exception.RecursoNaoEncontradoException;
import elicoes.api.es2.eleicoes_app.exception.RequisicaoInvalidaException;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> validacao(MethodArgumentNotValidException ex) {
        List<CampoErro> erros = new ArrayList<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> erros.add(new CampoErro(error.getField(), error.getDefaultMessage())));
        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> erros.add(new CampoErro(error.getObjectName(), error.getDefaultMessage())));
        return ResponseEntity.badRequest().body(new ErroResponse("Dados inválidos", erros));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroResponse> restricao(ConstraintViolationException ex) {
        List<CampoErro> erros = ex.getConstraintViolations().stream()
                .map(violacao -> new CampoErro(violacao.getPropertyPath().toString(), violacao.getMessage()))
                .toList();
        return ResponseEntity.badRequest().body(new ErroResponse("Dados inválidos", erros));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErroResponse> validacaoDeParametro(HandlerMethodValidationException ex) {
        List<CampoErro> erros = ex.getParameterValidationResults().stream()
                .flatMap(resultado -> resultado.getResolvableErrors().stream()
                        .map(error -> new CampoErro(resultado.getMethodParameter().getParameterName(), error.getDefaultMessage())))
                .toList();
        return ResponseEntity.badRequest().body(new ErroResponse("Dados inválidos", erros));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> corpoIlegivel() {
        return ResponseEntity.badRequest().body(new ErroResponse("Requisição inválida"));
    }

    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<ErroResponse> requisicao(RequisicaoInvalidaException ex) {
        return ResponseEntity.badRequest().body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> naoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(ConflitoDeDadosException.class)
    public ResponseEntity<ErroResponse> conflito(ConflitoDeDadosException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> integridade(DataIntegrityViolationException ex) {
        log.warn("Conflito de integridade ao persistir");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResponse("Dados já cadastrados"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> acessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErroResponse("Acesso negado"));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResponse> status(ResponseStatusException ex) {
        String mensagem = ex.getStatusCode().value() == HttpStatus.NOT_IMPLEMENTED.value()
                ? "Não implementado"
                : "Requisição recusada";
        return ResponseEntity.status(ex.getStatusCode()).body(new ErroResponse(mensagem));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> interno(Exception ex) {
        log.error("Falha não tratada", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroResponse("Erro interno"));
    }

}
