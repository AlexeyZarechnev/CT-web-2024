package ru.itmo.wp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.security.Guest;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class PostPage extends Page {

    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }
    
    @Guest
    @GetMapping("/post/{id}")
    public String postGet(@PathVariable String id, Model model) {
        long longId = -1;
        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {}
        model.addAttribute("comment", new Comment());
        model.addAttribute("post", postService.findById(longId));
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String addComment(@PathVariable String id, @Valid @ModelAttribute("comment") Comment comment, 
                        BindingResult bindingResult, Model model, HttpSession httpSession) {
        long longId = -1;
        try {
            longId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return "redirect:/post/" + id;
        }
        Post post = postService.findById(longId);
        model.addAttribute("post", post);

        if (bindingResult.hasErrors()) {
            return "PostPage";
        }
        
        if (post != null) {
            comment.setUser(getUser(httpSession));
            postService.addComment(post, comment);
            putMessage(httpSession, "Comment added successfully");
        }

        return "redirect:/post/" + id;
    }
    
    
}
