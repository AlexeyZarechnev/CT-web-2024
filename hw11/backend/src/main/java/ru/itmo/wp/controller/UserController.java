package ru.itmo.wp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.form.validator.UserCredentialsRegisterValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final JwtService  jwtService;

    private final UserCredentialsRegisterValidator registerValidator;

    public UserController(UserService userService, JwtService jwtService, UserCredentialsRegisterValidator registerValidator) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.registerValidator = registerValidator;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(String jwt) {
        if (jwtService.find(jwt) == null) {
            return null;
        }
        return userService.findAll();
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(registerValidator);
    }

    @PostMapping("/users")
    public String addUser(@RequestBody @Valid UserCredentials userCredentials, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        userService.register(userCredentials);
        return jwtService.create(userService.findByLoginAndPassword(userCredentials.getLogin(), userCredentials.getPassword()));

    }
    
}
