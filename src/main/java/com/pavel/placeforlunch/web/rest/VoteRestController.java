package com.pavel.placeforlunch.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.pavel.placeforlunch.model.Vote;
import com.pavel.placeforlunch.service.UserService;
import com.pavel.placeforlunch.service.VoteService;
import com.pavel.placeforlunch.web.json.Views;
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

import java.security.Principal;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = ProfileRestController.REST_URL + "/vote";
    private static final Logger LOG = LoggerFactory.getLogger(VoteRestController.class);

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @JsonView(Views.OnlyRestaurantId.class)
    @RequestMapping(method = RequestMethod.GET)
    public Vote get(Principal principal) {
        String username = principal.getName();
        LOG.debug("get for " + username);
        int id = userService.getIdByUsername(username);
        return voteService.getByUserId(id);
    }

    @JsonView(Views.WithRestaurant.class)
    @RequestMapping(method = RequestMethod.GET, params = "showRestaurant=true")
    public Vote getWithRestaurant(Principal principal) {
        String username = principal.getName();
        LOG.debug("get with restaurant for " + username);
        int id = userService.getIdByUsername(username);
        return voteService.getByUserId(id);
    }

    @JsonView(Views.WithRestaurant.class)
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity vote(
            @RequestBody Vote vote,
            Principal principal) {
        String username = principal.getName();
        LOG.debug("update for " + username);
        int userId = userService.getIdByUsername(username);
        vote = voteService.vote(vote.getRestaurantId(), userId);
        return ResponseEntity.status(HttpStatus.OK).body(vote);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity cancel(Principal principal) {
        String username = principal.getName();
        LOG.debug("cancel for " + username);
        int userId = userService.getIdByUsername(username);
        voteService.cancel(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
