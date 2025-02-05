package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.validator.JwtValidator;
import ru.itmo.wp.form.validator.PostValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final PostValidator postValidator;
    private final JwtValidator jwtValidator;
    private final JwtService jwtService;

    public PostController(PostService postService, PostValidator postValidator, JwtValidator jwtValidator, JwtService jwtService) {
        this.postService = postService;
        this.postValidator = postValidator;
        this.jwtValidator = jwtValidator;
        this.jwtService = jwtService;
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    @InitBinder("post")
    public void initPostBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(postValidator);
    }

    @PostMapping("/posts")
    public String addPost(@RequestBody @Valid Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        System.out.println(post.getUser().getLogin());
        
        postService.save(post);
        return "Post added";
    }
    

}
