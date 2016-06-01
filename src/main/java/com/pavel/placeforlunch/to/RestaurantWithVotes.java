package com.pavel.placeforlunch.to;

import com.fasterxml.jackson.annotation.JsonView;
import com.pavel.placeforlunch.model.Restaurant;
import com.pavel.placeforlunch.web.json.Views;

public class RestaurantWithVotes {

    private Integer id;
    private Integer votes;
    @JsonView(Views.WithRestaurant.class)
    private String name;

    public RestaurantWithVotes() {
    }

    public RestaurantWithVotes(Restaurant restaurant, Integer votes) {
        this.votes = votes;
        if (restaurant == null) {
            this.name = "Did not vote";
        } else {
            this.id = restaurant.getId();
            this.name = restaurant.getName();
        }
    }

    public RestaurantWithVotes(Integer id, Integer votes) {
        this.id = id;
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantWithVotes{" +
                "id=" + id +
                ", votes=" + votes +
                ", name='" + name + '\'' +
                '}';
    }
}
