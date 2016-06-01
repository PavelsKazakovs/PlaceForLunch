package com.pavel.placeforlunch;

import com.pavel.placeforlunch.model.Role;
import com.pavel.placeforlunch.model.User;
import com.pavel.placeforlunch.util.ModelMatcher;
import com.pavel.placeforlunch.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("SpellCheckingInspection")
public class UserTestData {

    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);
    public static final ModelMatcher<User, TestUser> MATCHER = new ModelMatcher<>(u -> (u instanceof TestUser ? (TestUser) u : new TestUser(u)), User.class);

    public static final User USER1 = new User(1, "Ismael Wilner", "ismael", "ismaelPass", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER2 = new User(2, "Noble Popovich", "noble", "noblePass", Role.ROLE_USER);
    public static final User USER3 = new User(3, "Estrella Gosnell", "estrella", "estrellaPass", Role.ROLE_USER);
    public static final User USER4 = new User(4, "Sondra Jorstad", "sondra", "sondraPass", Role.ROLE_USER);
    public static final User USER5 = new User(5, "Babette Merlin", "babette", "babettePass", Role.ROLE_USER);
    public static final User USER6 = new User(6, "Elke Weick", "elke", "elkePass", Role.ROLE_USER);
    public static final User USER7 = new User(7, "Otha Manchester", "otha", "othaPass", Role.ROLE_USER);
    public static final User USER8 = new User(8, "Cedric Lainez", "cedric", "cedricPass", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER9 = new User(9, "Jeanmarie Schuetz", "jeanmarie", "jeanmariePass", Role.ROLE_ADMIN);
    public static final User USER10 = new User(10, "Kelly Rothschild", "kelly", "kellyPass", Role.ROLE_USER);

    public static final User USER_NOT_EXIST = new User(99, "Not existing user", "notexist", "testPass");
    public static final User USER_WITH_VOTE = USER5;
    public static final User USER_WITH_EMPTY_VOTE = USER2;
    public static final User ADMIN = USER1;

    public static final User USER2_UPDATED = new User(2, "Updated User", "noble", "noblePassUpd", Role.ROLE_USER, Role.ROLE_ADMIN);
    public static final User USER2_UPDATED_NAME_PW = new User(2, "Updated User", "noble", "noblePassUpd", Role.ROLE_USER);
    public static final User USER2_UPDATED_NAME_PW_ROLES = new User(2, "Updated User", "noble", "noblePassUpd", Role.ROLE_USER, Role.ROLE_ADMIN);
    public static final User USER2_UPDATED_ID_NAME_PW_ROLES = new User(6, "Updated User", "noble", "noblePassUpd", Role.ROLE_USER, Role.ROLE_ADMIN);
    public static final User USER2_UPDATED_ID_NAME_USERNAME_PW_ROLES = new User(6, "Updated User", "nobleUpd", "noblePassUpd", Role.ROLE_USER, Role.ROLE_ADMIN);

    public static final List<User> ALL_USERS = Arrays.asList(
            USER1, USER2, USER3, USER4, USER5, USER6, USER7, USER8, USER9, USER10);

    public static User getCreated() {
        return new User("Created Test User", "created", "createdPass", Role.ROLE_USER);
    }

    public static User getCreatedNoRole() {
        User user = new User("Created Test User", "valid-Username", "createdPass");
        user.setRoles(null);
        return user;
    }

    public static User getCreatedNoRoleBadUsername() {
        User user = new User("Created Test User", "username with space", "createdPass");
        user.setRoles(null);
        return user;
    }

    public static User getCreatedWithAdminRole() {
        return new User("New test user with privelege", "newAdmin", "createdPass", Role.ROLE_USER, Role.ROLE_ADMIN);
    }

    private static class TestUser extends User {
        TestUser(User user) {
            super(user.getId(), user.getName(), user.getUsername(), user.getPassword());
            if (user.getRoles() != null) {
                this.roles = EnumSet.noneOf(Role.class);
                this.roles.addAll(user.getRoles());
            }
        }

        @Override
        public String toString() {
            return "TestUser{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", roles=" + roles +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestUser that = (TestUser) o;

            return Objects.equals(this.id, that.id) &&
                    Objects.equals(this.name, that.name) &&
                    Objects.equals(this.username, that.username) &&
                    comparePassword(this.password, that.password) &&
                    Objects.equals(this.roles, that.roles);
        }

        private boolean comparePassword(String rawPassword, String encodedPassword) {
            if (PasswordUtil.isEncoded(rawPassword)) {
                LOG.warn("Cannot compare two encoded passwords.");
            } else if (!PasswordUtil.isMatch(rawPassword, encodedPassword)) {
                LOG.error("Raw password " + rawPassword + " doesen't match encoded one.");
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + name.hashCode();
            result = 31 * result + username.hashCode();
            result = 31 * result + password.hashCode();
            result = 31 * result + roles.hashCode();
            return result;
        }
    }
}
