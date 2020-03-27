package com.mahim.reactspring.web;

import com.mahim.reactspring.model.Group;
import com.mahim.reactspring.model.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    private GroupRepository repository;

    public GroupController(GroupRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/groups")
    Collection<Group> groups() {
        return repository.findAll();
    }

    @GetMapping("/groups/{id}")
    ResponseEntity<?> getGroup(@PathVariable long id) {
        Optional<Group> group = repository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/groups/{id}")
    ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
        log.info("request to create group: {}", group);
        Group result = repository.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getId())).body(result);
    }

    @PutMapping("/groups/{id}")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        log.info("request to update group", group);
        Group result = repository.save(group);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/groups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("request to delete group: ", id);
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
