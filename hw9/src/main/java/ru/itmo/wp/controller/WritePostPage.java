package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.form.validator.PostsTagsValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.service.TagService;
import ru.itmo.wp.service.UserService;

import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class WritePostPage extends Page {
    private final UserService userService;
    private final TagService tagService;
    private final PostsTagsValidator postsTagsValidator;

    public WritePostPage(UserService userService, TagService tagService, PostsTagsValidator postsTagsValidator) {
        this.userService = userService;
        this.tagService = tagService;
        this.postsTagsValidator = postsTagsValidator;
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @GetMapping("/writePost")
    public String writePostGet(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("stringTags", "justpost");
        return "WritePostPage";
    }

    @InitBinder("stringTags")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postsTagsValidator);
    }

    @AnyRole({Role.Name.WRITER, Role.Name.ADMIN})
    @PostMapping("/writePost")
    public String writePostPost(@Valid @ModelAttribute("post") Post post,
                                @Valid @ModelAttribute("stringTags") String stringTags,
                                BindingResult bindingResult,
                                HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "WritePostPage";
        }

        Set<Tag> tags = new TreeSet<>();
        for (String strTag : stringTags.split("\\s+")) {
            Tag tag = new Tag();
            tag.setName(strTag);
            if (!tagService.isExist(tag)) {
                tagService.save(tag);
            } else {
                tag = tagService.findByName(tag.getName());
            }
            tags.add(tag);
        }

        userService.writePost(getUser(httpSession), tags, post);
        putMessage(httpSession, "You published new post");

        return "redirect:/myPosts";
    }
}
