package com.app.service.client.validator;

import com.app.service.client.config.exceptions.ValidateException;
import com.app.service.client.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.util.Set;

@Component
public class CommonValidator {

    @Autowired
    private MessageService messageService;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public <T> void validateInput(T t) throws ValidateException {
        Set<ConstraintViolation<T>> violations = validator.validate(t);
        for (ConstraintViolation<T> violation : violations) {
            if (violation.getConstraintDescriptor().getAnnotation() instanceof NotNull) {
                throw new ValidateException(
                    messageService.getMessage("error.code.empty", violation.getMessage()));
            }
            if (violation.getConstraintDescriptor().getAnnotation() instanceof NotBlank) {
                throw new ValidateException(
                    messageService.getMessage("error.code.empty", violation.getMessage()));
            }
            if (violation.getConstraintDescriptor().getAnnotation() instanceof NotEmpty) {
                throw new ValidateException(
                    messageService.getMessage("error.code.empty", violation.getMessage()));
            }
            if (violation.getConstraintDescriptor().getAnnotation() instanceof PositiveOrZero) {
                throw new ValidateException(
                    messageService.getMessage("error.code.invalid.number", violation.getMessage()));
            }
            if (violation.getConstraintDescriptor().getAnnotation() instanceof Size) {
                throw new ValidateException(messageService.getMessage("error.code.maxlength",
                    violation.getPropertyPath().toString(),
                    violation.getConstraintDescriptor().getAttributes().get("max")));
            }
        }
    }
}
