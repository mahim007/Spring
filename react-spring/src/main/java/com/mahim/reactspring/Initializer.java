package com.mahim.reactspring;

import com.mahim.reactspring.model.Event;
import com.mahim.reactspring.model.Group;
import com.mahim.reactspring.model.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

@Component
public class Initializer implements CommandLineRunner {
    private final GroupRepository repository;

    public Initializer(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("java", "js", "c", "python").forEach(name ->
                repository.save(new Group(name))
                );
        Group group = repository.findByName("java");
        Event event = Event.builder().title("Full stack java developer")
                .description("expert on building application usind spring boot with react")
                .date(Instant.parse("2018-12-12T18:00:00.000Z"))
                .build();
        group.setEvents(Collections.singleton(event));
        repository.save(group);
        repository.findAll().forEach(System.out::println);
    }
}
