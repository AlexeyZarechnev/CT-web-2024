package ru.itmo.wp.service;

import org.springframework.stereotype.Service;

import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public boolean isExist(Tag tag) {
        return tagRepository.existsByName(tag.getName());
    }

    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    public Tag findByName(String name) {
        return tagRepository.findByName(name);
    }
}
