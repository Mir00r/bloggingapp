package com.mir00r.bloggingapp.repository;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.models.UserBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
public interface UserBlogRepository extends JpaRepository<UserBlog, Long> {
    List<UserBlog> findByBlog(Blog blog);

    List<UserBlog> findByUser(User user);
}
