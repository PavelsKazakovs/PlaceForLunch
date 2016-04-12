package com.pavel.placeforlunch.model;

import java.util.Set;

public class User extends NamedEntity {

    private Restaurant vote;
    private Set<Role> roles;

    public Restaurant getVote() {
        return vote;
    }

    public void setVote(Restaurant vote) {
        this.vote = vote;
    }

    public Set<Role> getRoles() {
        return roles;
    }

}
