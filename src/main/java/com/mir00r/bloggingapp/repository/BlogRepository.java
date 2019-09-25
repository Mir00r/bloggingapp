package com.mir00r.bloggingapp.repository;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findBlogByUser(User user);

    @Query(value = "SELECT * from blog where user_id != :userId", nativeQuery = true)
    List<Blog> findOtherBlog(@Param("userId") long userId);
}
