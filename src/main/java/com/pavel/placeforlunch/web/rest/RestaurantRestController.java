package com.pavel.placeforlunch.web.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.service.RestaurantService;
import com.pavel.placeforlunch.web.json.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/v1/poll/restaurants";
    private static final Logger LOG = LoggerFactory.getLogger(RestaurantRestController.class);

    @Autowired
    private RestaurantService service;

    @JsonView(Views.WithDishes.class)
    @RequestMapping(method = RequestMethod.GET, params = "showDishes=true")
    public List<Restaurant> getAllFetchDishes() {
        LOG.debug("get all with dishes");
        return service.getAllFetchDishes();
    }

    @JsonView(Views.Basic.class)
    @RequestMapping(method = RequestMethod.GET)
    public List<Restaurant> getAll() {
        LOG.debug("get all");
        return service.getAll();
    }

    @JsonView(Views.WithDishes.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = "showDishes=true")
    public Restaurant getFetchDishes(@PathVariable int id) {
        LOG.debug("get " + id);
        return service.getFetchDishes(id);
    }

    @JsonView(Views.Basic.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Restaurant get(@PathVariable int id) {
        LOG.debug("get " + id);
        return service.get(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        LOG.debug("create " + restaurant);
        restaurant.setId(null);
        Restaurant created = service.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int id) {
        LOG.debug("delete " + id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Restaurant restaurant,
                                 @PathVariable int id) {
        LOG.debug("update " + id);
        restaurant.setId(id);
        service.update(restaurant);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
