package com.pavel.placeforlunch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.pavel.placeforlunch.web.json.Views;

import javax.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private int userId;

    @Column(name = "restaurant_id", insertable = false, updatable = false)
    @JsonView(Views.OnlyRestaurantId.class)
    private Integer restaurantId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @JsonView(Views.WithRestaurant.class)
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", restaurant=" + restaurant +
                '}';
    }
}
