package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserPage extends Page {
    
    @GetMapping("/user/{id}")
    public String user(@PathVariable String id, Model model) {
        long longId = -1;
        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {}
        model.addAttribute("u", userService.findById(longId));
        return "UserPage";
    }
}
