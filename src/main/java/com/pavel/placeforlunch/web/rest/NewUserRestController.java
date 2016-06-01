package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.service.UserService;
import com.pavel.placeforlunch.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(NewUserRestController.REST_URL)
public class NewUserRestController {
    static final String REST_URL = "/rest/v1/new-user";
    private static final Logger LOG = LoggerFactory.getLogger(NewUserRestController.class);

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTO> create(@Valid @RequestBody UserTO userTO) {
        LOG.info("create " + userTO);
        userTO.setId(null);
        UserTO created = service.save(userTO);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/v1/profile")
                .buildAndExpand(created.getName()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
