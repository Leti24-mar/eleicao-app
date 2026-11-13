package elicoes.api.es2.eleicoes_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumeroCompativelValidator.class)
public @interface NumeroCompativel {

    String message() default "Número incompatível com o cargo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
