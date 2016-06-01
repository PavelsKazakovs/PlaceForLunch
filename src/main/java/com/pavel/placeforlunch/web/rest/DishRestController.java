package com.pavel.placeforlunch.web.rest;

import com.pavel.placeforlunch.model.Dish;
import com.pavel.placeforlunch.service.DishService;
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
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    static final String REST_URL = RestaurantRestController.REST_URL + "/{restaurantId}/dishes";
    private static final Logger LOG = LoggerFactory.getLogger(DishRestController.class);

    @Autowired
    private DishService service;

    @RequestMapping(method = RequestMethod.GET)
    public List<Dish> getAllForRestaurant(@PathVariable int restaurantId) {
        LOG.info("Get all for restaurant " + restaurantId);
        return service.getByRestaurantId(restaurantId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Dish get(@PathVariable int restaurantId,
                    @PathVariable int id) {
        LOG.info("get {} for {}", id, restaurantId);
        return service.get(id, restaurantId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@RequestBody Dish dish,
                                       @PathVariable int restaurantId) {
        LOG.info("create {} for restaurant {}", dish, restaurantId);
        dish.setId(null);
        Dish created = service.save(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL.replace("{restaurantId}", String.valueOf(restaurantId)) +
                        "/" + created.getId())
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable int restaurantId,
                                 @PathVariable int id) {
        LOG.debug("delete " + id);
        service.delete(id, restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody Dish dish,
                                 @PathVariable int restaurantId,
                                 @PathVariable int id) {
        LOG.debug("update " + id);
        dish.setId(id);
        service.update(dish, restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
