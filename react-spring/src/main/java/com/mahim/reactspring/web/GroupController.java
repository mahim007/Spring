package com.mahim.reactspring.web;

import com.mahim.reactspring.model.Group;
import com.mahim.reactspring.model.GroupRepository;
import com.mahim.reactspring.model.User;
import com.mahim.reactspring.model.UserRepository;
import com.sun.org.apache.bcel.internal.generic.FSUB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GroupController {
    private final Logger log = LoggerFactory.getLogger(GroupController.class);
    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public GroupController(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/groups")
    Collection<Group> groups(Principal principal) {
        return groupRepository.findAllByUserId(principal.getName());
    }

    @GetMapping("/groups/{id}")
    ResponseEntity<?> getGroup(@PathVariable long id) {
        Optional<Group> group = groupRepository.findById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/groups")
    ResponseEntity<Group> createGroup(@Valid @RequestBody Group group, @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("request to create group: {}", group);

        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        Optional<User> user = userRepository.findById(userId);
        group.setUser(user.orElse(new User(userId, details.get("name").toString(), details.get("email").toString())));

        Group result = groupRepository.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getId())).body(result);
    }

    @PutMapping("/groups/{id}")
    ResponseEntity<Group> updateGroup(@Valid @RequestBody Group group) {
        log.info("request to update group", group);
        Group result = groupRepository.save(group);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/groups/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
        log.info("request to delete group: ", id);
        groupRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
