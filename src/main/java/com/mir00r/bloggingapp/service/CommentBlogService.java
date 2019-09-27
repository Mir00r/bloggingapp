package com.mir00r.bloggingapp.service;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.models.CommentBlog;
import com.mir00r.bloggingapp.repository.CommentBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
@Service
@Transactional
public class CommentBlogService {
    @Autowired
    private CommentBlogRepository commentBlogRepository;
    @Autowired
    private UserService userService;

    public List<CommentBlog> findAll() {
        List<CommentBlog> blogList = new ArrayList<>();
        blogList = commentBlogRepository.findAll();
        return blogList;
    }

    public CommentBlog findUserBlog(Long id) {
        return commentBlogRepository.getOne(id);
    }

    public void save(CommentBlog commentBlog) {
        commentBlog.setUser(getUser());
        commentBlogRepository.save(commentBlog);
    }

    public void delete(Long id) {
        commentBlogRepository.deleteById(id);
    }

    public List<CommentBlog> findByBlog(Blog blog) {
        return commentBlogRepository.findByBlog(blog);
    }

    public List<CommentBlog> findByUser(User user) {
        return commentBlogRepository.findByUser(user);
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }
}
