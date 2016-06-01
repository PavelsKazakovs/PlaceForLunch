package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.to.UserTO;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service for managing user entries.
 */
public interface UserService {

    List<User> getAll();

    User get(int id) throws ResourceNotFoundException;

    User getByUsername(String username) throws ResourceNotFoundException;

    void delete(int id) throws ResourceNotFoundException;

    void deleteByUsername(String username) throws ResourceNotFoundException;

    User save(User user);

    void update(User user) throws ResourceNotFoundException;

    User updateByUsername(User user) throws ResourceNotFoundException;

    int getIdByUsername(String username) throws ResourceNotFoundException;

    void updateByUsername(UserTO userTO) throws ResourceNotFoundException;

    /**
     * Creates user entry with {@link com.pavel.placeforlunch.model.Role#ROLE_USER ROLE_USER}
     */
    UserTO save(UserTO userTO);
}
