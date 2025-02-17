package ru.itmo.wp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.itmo.wp.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
    boolean existsByName(String name);

    Tag findByName(String name);
}
