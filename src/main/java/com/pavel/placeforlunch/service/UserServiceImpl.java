package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Role;
import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.repository.UserRepository;
import com.pavel.placeforlunch.to.UserTO;
import com.pavel.placeforlunch.util.EntityName;
import com.pavel.placeforlunch.util.UserUtil;
import com.pavel.placeforlunch.util.exception.ExceptionUtil;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public User get(int id) throws ResourceNotFoundException {
        return ExceptionUtil.check(repository.get(id), EntityName.USER, id);
    }

    @Override
    public User getByUsername(String username) throws ResourceNotFoundException {
        return ExceptionUtil.check(repository.getByUsername(username),
                EntityName.USER, "username=" + username);
    }

    @Override
    @Transactional
    public void delete(int id) throws ResourceNotFoundException {
        ExceptionUtil.check(repository.delete(id), EntityName.USER, id);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) throws ResourceNotFoundException {
        ExceptionUtil.check(repository.deleteByUsername(username),
                EntityName.USER, "username=" + username);
    }

    @Override
    @Transactional
    public User save(User user) {
        user = UserUtil.prepareToSave(user);
        return repository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) throws ResourceNotFoundException {
        user = UserUtil.prepareToSave(user);
        ExceptionUtil.check(repository.save(user), EntityName.USER, user.getId());
    }

    @Override
    @Transactional
    public User updateByUsername(User user) throws ResourceNotFoundException {
        user = UserUtil.prepareToSave(user);
        return ExceptionUtil.check(repository.updateByUsername(user),
                EntityName.USER, "username=" + user.getUsername());
    }

    @Override
    public int getIdByUsername(String username) throws ResourceNotFoundException {
        return ExceptionUtil.check(repository.getIdByUsername(username),
                EntityName.USER, "username=" + username);
    }

    @Override
    @Transactional
    public void updateByUsername(UserTO userTO) {
        User user = getByUsername(userTO.getUsername());
        repository.save(UserUtil.updateFromTO(user, userTO));
    }

    @Override
    @Transactional
    public UserTO save(UserTO userTO) {
        User user = new User(userTO.getName(), userTO.getUsername(),
                userTO.getPassword(), Role.ROLE_USER);
        user = UserUtil.prepareToSave(user);
        User created = repository.save(user);
        return UserUtil.asTO(created);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByUsername(username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getRoles());
    }
}
