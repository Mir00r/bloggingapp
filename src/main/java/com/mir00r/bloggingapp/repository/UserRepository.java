package com.mir00r.bloggingapp.repository;

import com.mir00r.bloggingapp.models.Role;
import com.mir00r.bloggingapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author mir00r on 2019-09-21
 * @project IntelliJ IDEA
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByActive(int active);

    @Query(value = "SELECT * from user where active = :activeStatus and id != :loggedUId", nativeQuery = true)
    List<User> findAllUser(@Param("activeStatus") long activeStatus, @Param("loggedUId") long loggedUId);

    @Query(value = "SELECT * from user where id != :loggedUId", nativeQuery = true)
    List<User> findAllExcept(@Param("loggedUId") long loggedUId);
}
