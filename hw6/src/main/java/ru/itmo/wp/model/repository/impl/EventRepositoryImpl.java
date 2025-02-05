package ru.itmo.wp.model.repository.impl;

import java.util.List;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

public class EventRepositoryImpl extends AbstractRepositoryImpl implements EventRepository {
    private final static String TABLE_NAME = "Event";

    @Override
    public void save(Event event) {
        save(List.of("userId", "type"), List.of(Long.toString(event.getUserId()), event.getType().toString()), TABLE_NAME);
    }
    
}
