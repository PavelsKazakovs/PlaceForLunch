package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminRestController.class);
    static final String REST_URL = "/rest/v1/admin/users";

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAll() {
        LOG.info("Get all users");
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        LOG.info("create " + user);
        user.setId(null);
        User created = service.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + user.getUsername())
                .buildAndExpand(created.getName()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User get(@PathVariable String username) {
        LOG.info("get " + username);
        return service.getByUsername(username);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.PUT)
    public ResponseEntity update(@Valid @RequestBody User user,
                                 @PathVariable String username) {
        LOG.info("update " + username);
        user.setUsername(username);
        service.updateByUsername(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String username) {
        LOG.info("delete " + username);
        service.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
