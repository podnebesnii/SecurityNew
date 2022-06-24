package com.firstboot.springboot.model;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= PersonAgeConstraint.PersonAgeConstraintValidator.class)
public @interface PersonAgeConstraint {
    String message() default "{Отрицательное\\u0020значение}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PersonAgeConstraintValidator implements ConstraintValidator<PersonAgeConstraint, Integer> {
        @Override
        public boolean isValid(Integer age, ConstraintValidatorContext constraintValidatorContext) {
            return age > 0;
        }
    }
}

