package com.pavel.placeforlunch.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User extends NamedEntity {

    @Pattern(regexp = "^[A-Za-z0-9._\\-]+$", message = "can have only letters, numbers and ._-")
    protected String username;

    @Size(min = 5, max = 64)
    protected String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<Role> roles;

    public User() {
    }

    public User(String name, String username, String password, Role... roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = EnumSet.noneOf(Role.class);
        this.roles.addAll(Arrays.asList(roles));
    }

    public User(int id, String name, String username, String password, Role... roles) {
        this(name, username, password, roles);
        this.id = id;
    }

    public User(int id, String name, String username, String password) {
        this(name, username, password);
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @SuppressWarnings("WeakerAccess")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("WeakerAccess")

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        List<Role> rolesSorted = roles == null ? null : roles.stream().sorted().collect(Collectors.toList());
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + rolesSorted +
                '}';
    }
}
