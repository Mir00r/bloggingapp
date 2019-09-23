package com.mir00r.bloggingapp.repository;

import com.mir00r.bloggingapp.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
