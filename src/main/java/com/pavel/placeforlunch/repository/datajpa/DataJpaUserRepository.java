package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaUserRepository implements UserRepository {

    private static final Sort SORT_ID = new Sort("id");

    @Autowired
    private ProxyUserRepository proxy;

    @Override
    public User get(int id) {
        return proxy.findOne(id);
    }

    @Override
    public User getByUsername(String username) {
        return proxy.findByUsernameIgnoreCase(username);
    }

    @Override
    public List<User> getAll() {
        return proxy.findAll(SORT_ID);
    }

    @Override
    @Transactional
    public User save(User user) {
        if (!user.isNew() && get(user.getId()) == null) {
            return null;
        }
        return proxy.save(user);
    }

    @Override
    public boolean delete(int id) {
        return proxy.delete(id) != 0;
    }

    @Override
    public boolean deleteByUsername(String username) {
        return proxy.deleteByUsername(username) != 0;
    }

    @Override
    @Transactional
    public User updateByUsername(User user) {
        Integer id = proxy.findIdByUsername(user.getUsername());
        if (id == null) {
            return null;
        }
        user.setId(id);
        return proxy.save(user);
    }

    @Override
    public Integer getIdByUsername(String username) {
        return proxy.findIdByUsername(username);
    }

}
