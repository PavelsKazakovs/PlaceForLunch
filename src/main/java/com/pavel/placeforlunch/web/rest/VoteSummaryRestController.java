package com.pavel.placeforlunch.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.service.VoteService;
import com.pavel.placeforlunch.to.RestaurantWithVotes;
import com.pavel.placeforlunch.web.json.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = VoteSummaryRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteSummaryRestController {
    static final String REST_URL = "/rest/v1/poll/vote-summary";
    private static final Logger LOG = LoggerFactory.getLogger(VoteSummaryRestController.class);

    @Autowired
    private VoteService voteService;

    @JsonView(Views.Basic.class)
    @RequestMapping(method = RequestMethod.GET)
    public List<RestaurantWithVotes> getSummary() {
        LOG.debug("get");
        Map<Restaurant, Integer> summary = voteService.getVoteCountsByRestaurant();
        return summary.entrySet().stream()
                .map(entry -> new RestaurantWithVotes(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @JsonView(Views.WithRestaurant.class)
    @RequestMapping(method = RequestMethod.GET, params = "showRestaurants=true")
    public List<RestaurantWithVotes> getWithRestaurantDetails() {
        LOG.debug("get with restaurant details");
        return getSummary();
    }
}
