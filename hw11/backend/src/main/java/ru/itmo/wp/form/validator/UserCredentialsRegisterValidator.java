package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.UserService;

@Component
public class UserCredentialsRegisterValidator implements Validator {
    private final UserService userService;

    public UserCredentialsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return UserCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserCredentials enterForm = (UserCredentials) target;
            String login = enterForm.getLogin().trim();
            String password = enterForm.getPassword().trim();

            if (login.isEmpty()) {
                errors.rejectValue("login", "login.empty", "login must not be empty");
                return;
            }
            if (login.length() < 2 || login.length() > 20) {
                errors.rejectValue("login", "login.length", "login length must be between 2 and 20");
                return;
            }
            if (!userService.isLoginVacant(login)) {
                errors.rejectValue("login", "login.already-exists", "login already exists");
                return;
            }

            if (password.isEmpty()) {
                errors.rejectValue("password", "password.empty", "password must not be empty");
                return;
            }
            if (password.length() < 2) {
                errors.rejectValue("password", "password.length", "password length must be at least 2");
                return;
            }
        }
    }
}
