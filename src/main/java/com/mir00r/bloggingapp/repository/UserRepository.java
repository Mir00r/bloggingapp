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

    /*@Query(
            nativeQuery = true,
            value = "SELECT * FROM user a LEFT JOIN core_user_profile b ON b.user_id = a.id " +
                    "WHERE a.username LIKE %:keywords% OR b.real_name LIKE %:keywords% OR b.email LIKE %:keywords% ORDER BY ?#{#pageable}",
            countQuery = "SELECT count(*) FROM user a LEFT JOIN core_user_profile b ON b.user_id = a.id " +
                    "WHERE a.username LIKE %:keywords% OR b.real_name LIKE %:keywords% OR b.email LIKE %:keywords%"
    )
    Page<User> search(@Param(value = "keywords") String keywords, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM core_user_role WHERE user_id = :userId", nativeQuery = true)
    void deleteRolesByUserId(@Param(value = "userId") long userId);*/
}
