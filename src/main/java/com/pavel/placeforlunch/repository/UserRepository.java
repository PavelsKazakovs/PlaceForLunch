package com.pavel.placeforlunch.repository;

import com.pavel.placeforlunch.model.User;

import java.util.List;

public interface UserRepository {

    User get(int id);

    User getByUsername(String username);

    List<User> getAll();

    User save(User user);

    boolean delete(int id);

    boolean deleteByUsername(String username);

    User updateByUsername(User user);

    Integer getIdByUsername(String username);
}
