package com.mir00r.bloggingapp.repository;

import com.mir00r.bloggingapp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mir00r on 2019-09-21
 * @project IntelliJ IDEA
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE role SET menu_ids = :menuIds WHERE id = :roleId", nativeQuery = true)
    void updateMenus(@Param("roleId") long roleId, @Param("menuIds") String menuIds);

}
