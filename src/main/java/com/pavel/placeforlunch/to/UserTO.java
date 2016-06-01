package com.pavel.placeforlunch.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty
    private String name;

    @Pattern(regexp = "^[A-Za-z0-9._\\-]+$", message = "can have only letters, numbers and ._-")
    private String username;

    /**
     * Can be changed through JSON, but cannot be read.
     */
    @NotEmpty
    @Size(min = 5, max = 64, message = "must be between 5 and 64 characters")
    private String password;

    public UserTO() {
    }

    public UserTO(Integer id,
                  @JsonProperty String name,
                  @JsonProperty String username,
                  @JsonProperty String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
