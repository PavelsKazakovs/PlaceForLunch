package com.pavel.placeforlunch.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.pavel.placeforlunch.web.json.Views;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant extends NamedEntity {

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonView(Views.WithDishes.class)
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Restaurant(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
