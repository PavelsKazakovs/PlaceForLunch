package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.service.UserService;
import com.pavel.placeforlunch.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileRestController {
    static final String REST_URL = "/rest/v1/profile";
    private static final Logger LOG = LoggerFactory.getLogger(ProfileRestController.class);

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    public User get(Principal principal) {
        String username = principal.getName();
        LOG.info("get " + username);
        return service.getByUsername(username);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(@Valid @RequestBody UserTO userTO,
                                 Principal principal) {
        String username = principal.getName();
        LOG.info("update " + username);
        userTO.setUsername(username);
        service.updateByUsername(userTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity delete(Principal principal) {
        String username = principal.getName();
        LOG.info("delete " + username);
        service.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
