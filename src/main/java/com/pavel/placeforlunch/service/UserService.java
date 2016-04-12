package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.util.exception.NotFoundException;

import java.util.List;

/**
 * Service for managing user entries. Used by admin.
 */
public interface UserService {

    List<User> getAll();

    User getById(int id) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    User save(User user);

    User update(User user) throws NotFoundException;
}
