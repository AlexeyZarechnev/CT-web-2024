package ru.itmo.wp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ru.itmo.wp.domain.Notice;

@Controller
public class NoticePage extends Page {

    @GetMapping("/notice")
    public String noticeGet(Model model) {
        model.addAttribute("notice", new Notice());
        return "NoticePage";
    }
    
    @PostMapping("/notice")
    public String createNotice(@Valid @ModelAttribute("notice") Notice notice,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.addNotice(notice);
        setMessage(httpSession, "Notice added successfully");

        return "redirect:/";
    }
}
