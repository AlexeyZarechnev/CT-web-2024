package ru.itmo.wp.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ru.itmo.wp.service.UserService;

@Controller
public class UsersPage extends Page {

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/enable/{id}")
    public String enableUser(@PathVariable long id, HttpSession httpSession) {
        if (userService.findById(id) == null) {
            setMessage(httpSession, "User not found");
            return "redirect:/users/all";
        }

        userService.enableUser(id);
        return "redirect:/users/all";
    }

    @PostMapping("/users/disable/{id}")
    public String disableUser(@PathVariable long id, HttpSession httpSession) {
        if (userService.findById(id) == null) {
            setMessage(httpSession, "User not found");
            return "redirect:/users/all";
        }

        userService.disableUser(id);
        return "redirect:/users/all";
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }
}
