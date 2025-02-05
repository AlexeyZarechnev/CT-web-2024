package ru.itmo.wp.form.validator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostsTagsValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        String tags = (String) target;
        String[] tagsArray = tags.split("\\s+");
        Set<String> tagsSet = new HashSet<>();
        for (String tag : tagsArray) {
            if (!tag.matches("[a-z]+")) {
                errors.rejectValue(null, "tags.invalid", "Invalid tags");
            } else if (tagsSet.contains(tag)) {
                errors.rejectValue(null, "tags.duplicate", "Duplicate tags");
            } else {
                tagsSet.add(tag);
            }
        }
    }
    
}
