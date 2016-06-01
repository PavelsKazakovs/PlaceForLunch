package com.pavel.placeforlunch.service;

import com.pavel.placeforlunch.model.Role;
import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.to.UserTO;
import com.pavel.placeforlunch.util.UserUtil;
import com.pavel.placeforlunch.util.exception.ResourceNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static com.pavel.placeforlunch.UserTestData.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends ServiceTest {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    protected UserService userService;

    @Test
    public void getAll() throws Exception {
        List<User> actual = userService.getAll();
        MATCHER.assertCollectionEquals(ALL_USERS, actual);
    }

    @Test
    public void get() throws Exception {
        User actual = userService.get(USER_WITH_VOTE.getId());
        MATCHER.assertEquals(USER_WITH_VOTE, actual);
        LOG.debug("expected: " + USER_WITH_VOTE);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with id=" + USER_NOT_EXIST.getId());
        User actual = userService.get(USER_NOT_EXIST.getId());
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getByUsername() throws Exception {
        User actual = userService.getByUsername(USER1.getUsername());
        MATCHER.assertEquals(USER1, actual);
        LOG.debug("expected: " + USER1);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getByUsernameNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with username=" + USER_NOT_EXIST.getUsername());
        User actual = userService.getByUsername(USER_NOT_EXIST.getUsername());
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER2.getId());
        List<User> expected = Arrays.asList(USER1, USER3, USER4, USER5, USER6, USER7, USER8, USER9, USER10);
        List<User> actual = userService.getAll();
        MATCHER.assertCollectionEquals(expected, actual);
        LOG.debug("expected: " + expected);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with id=" + USER_NOT_EXIST.getId());
        userService.delete(USER_NOT_EXIST.getId());
    }

    @Test
    public void deleteByUsername() throws Exception {
        userService.deleteByUsername(USER2.getUsername());
        List<User> expected = Arrays.asList(USER1, USER3, USER4, USER5, USER6, USER7, USER8, USER9, USER10);
        List<User> actual = userService.getAll();
        MATCHER.assertCollectionEquals(expected, actual);
        LOG.debug("expected: " + expected);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void deleteByUsernameNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with username=" + USER_NOT_EXIST.getUsername());
        userService.deleteByUsername(USER_NOT_EXIST.getUsername());
    }

    @Test
    public void save() throws Exception {
        int expectedId = ALL_USERS.size() + 1;
        User created = getCreated();
        User returned = userService.save(UserUtil.prepareToSave(created));
        User actual = userService.get(expectedId);
        MATCHER.assertEquals(created, returned);
        MATCHER.assertEquals(created, actual);
        MATCHER.assertCollectionEquals(Arrays.asList(USER1, USER2, USER3, USER4, USER5,
                USER6, USER7, USER8, USER9, USER10, created), userService.getAll());
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + created);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void update() throws Exception {
        userService.update(UserUtil.prepareToSave(USER2_UPDATED));
        User actual = userService.get(USER2.getId());
        MATCHER.assertEquals(USER2_UPDATED, actual);
        LOG.debug("expected: " + USER2_UPDATED);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with id=" + USER_NOT_EXIST.getId());
        userService.update(UserUtil.prepareToSave(USER_NOT_EXIST));
    }

    @Test
    public void updateByUsername() throws Exception {
        // Service should ignore ID field
        User returned = userService.updateByUsername(USER2_UPDATED_ID_NAME_PW_ROLES);
        User actual = userService.get(USER2.getId());
        MATCHER.assertEquals(USER2_UPDATED, actual);
        MATCHER.assertEquals(USER2_UPDATED, returned);
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + USER2_UPDATED);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void updateByUsernameNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with username=" + USER_NOT_EXIST.getUsername());
        User actual = userService.updateByUsername(USER_NOT_EXIST);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getIdByUsername() throws Exception {
        int actual = userService.getIdByUsername(USER2.getUsername());
        Assert.assertEquals(2, actual);
        LOG.debug("expected: " + USER2.getId());
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void getIdByUsernameNotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with username=" + USER_NOT_EXIST.getUsername());
        int actual = userService.getIdByUsername(USER_NOT_EXIST.getUsername());
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void updateByUsernameWithTO() throws Exception {
        UserTO userTO = UserUtil.asTO(USER2_UPDATED_ID_NAME_PW_ROLES);
        userService.updateByUsername(userTO);
        User actual = userService.get(USER2.getId());
        MATCHER.assertEquals(USER2_UPDATED_NAME_PW, actual);
        LOG.debug("expected: " + USER2_UPDATED_NAME_PW);
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void updateByUsernameWithTONotFound() throws Exception {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Not found user with username=" + USER_NOT_EXIST.getUsername());
        UserTO userTO = UserUtil.asTO(USER_NOT_EXIST);
        userService.updateByUsername(userTO);
        User actual = userService.get(USER2.getId());
        LOG.debug("  actual: " + actual);
    }

    @Test
    public void saveWithTONotSavingAsAdmin() throws Exception {
        int expectedId = ALL_USERS.size() + 1;
        UserTO created = UserUtil.asTO(getCreatedWithAdminRole());
        UserTO returned = userService.save(created);
        User actualUser = userService.get(expectedId);
        User expectedUser = new User(expectedId, created.getName(), created.getUsername(),
                created.getPassword(), Role.ROLE_USER);
        MATCHER.assertEquals(expectedUser, actualUser);
        MATCHER.assertCollectionEquals(Arrays.asList(USER1, USER2, USER3, USER4, USER5,
                USER6, USER7, USER8, USER9, USER10, expectedUser), userService.getAll());
        LOG.debug("returned: " + returned);
        LOG.debug("expected: " + created);
        LOG.debug("  actual: " + actualUser);
    }

}
