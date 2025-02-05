package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.itmo.wp.domain.Post;
import ru.itmo.wp.service.PostService;

@Component
public class PostValidator implements Validator {
    private final PostService postService;

    public PostValidator(PostService postService) {
        this.postService = postService;
    }

    public boolean supports(Class<?> clazz) {
        return Post.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
       if (!errors.hasErrors()) {
            Post post = (Post) target;
            String title = post.getTitle().trim();
            String text = post.getText().trim();
            if (title.isEmpty()) {
                errors.rejectValue("title", "title.empty", "title must not be empty");
                return;
            }
            if (title.length() > 255) {
                errors.rejectValue("title", "title.length", "title length must be at most 255");
                return;
            }
            if (!postService.isTitleVacant(title)) {
                errors.rejectValue("title", "title.already-exists", "title already exists");
                return;
            }
            if (text.isEmpty()) {
                errors.rejectValue("text", "text.empty", "text must not be empty");
                return;
            }
            if (text.length() > 65536) {
                errors.rejectValue("text", "text.length", "text length must be at most 65536");
                return;
            }
       }
    }
}
