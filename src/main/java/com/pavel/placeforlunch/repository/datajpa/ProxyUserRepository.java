package com.pavel.placeforlunch.repository.datajpa;

import com.pavel.placeforlunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProxyUserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1")
    int delete(int id);

    User findByUsernameIgnoreCase(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.username=?1")
    int deleteByUsername(String username);

    @SuppressWarnings("SpringDataJpaMethodInconsistencyInspection")
    @Query("SELECT u.id FROM User u WHERE lower(u.username)=lower(?1)")
    Integer findIdByUsername(String username);
}
