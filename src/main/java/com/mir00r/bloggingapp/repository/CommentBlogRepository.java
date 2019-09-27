package com.mir00r.bloggingapp.repository;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.models.CommentBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
public interface CommentBlogRepository extends JpaRepository<CommentBlog, Long> {
    List<CommentBlog> findByBlog(Blog blog);

    List<CommentBlog> findByUser(User user);
}
