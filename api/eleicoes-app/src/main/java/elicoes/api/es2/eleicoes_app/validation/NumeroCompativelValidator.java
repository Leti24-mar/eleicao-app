package elicoes.api.es2.eleicoes_app.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumeroCompativelValidator implements ConstraintValidator<NumeroCompativel, DadosNumeroCandidato> {

    @Override
    public boolean isValid(DadosNumeroCandidato dados, ConstraintValidatorContext context) {
        if (dados == null || dados.tipo() == null || dados.numero() == null || dados.numero().isBlank()) {
            return true;
        }
        if (RegrasNumeroCandidato.compativel(dados.tipo(), dados.numero())) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Número incompatível com o cargo")
                .addPropertyNode("numero")
                .addConstraintViolation();
        return false;
    }

}
