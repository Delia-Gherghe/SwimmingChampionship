package com.example.swimmingchampionship.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FinalStageValidator.class)
@Documented
public @interface FinalStage {
    String message() default "Round must be semifinal or final!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
