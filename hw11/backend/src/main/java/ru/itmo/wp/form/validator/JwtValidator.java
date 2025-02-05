package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.itmo.wp.service.JwtService;

@Component
public class JwtValidator implements Validator {

    private final JwtService jwtService;

    public JwtValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            String token = (String) target;
            if (jwtService.find(token) == null) {
                errors.rejectValue("token", "token.invalid", "invalid token");
            }
        }
    }
    
}
