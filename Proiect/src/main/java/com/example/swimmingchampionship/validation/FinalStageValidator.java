package com.example.swimmingchampionship.validation;

import com.example.swimmingchampionship.model.RoundType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FinalStageValidator implements ConstraintValidator<FinalStage, RoundType> {

    @Override
    public boolean isValid(RoundType roundType, ConstraintValidatorContext context) {
        return roundType == RoundType.Semifinal || roundType == RoundType.Final;
    }
}
