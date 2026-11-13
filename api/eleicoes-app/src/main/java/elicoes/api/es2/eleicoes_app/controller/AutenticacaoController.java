package elicoes.api.es2.eleicoes_app.controller;

import elicoes.api.es2.eleicoes_app.dto.LoginRequest;
import elicoes.api.es2.eleicoes_app.dto.LoginResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

}
