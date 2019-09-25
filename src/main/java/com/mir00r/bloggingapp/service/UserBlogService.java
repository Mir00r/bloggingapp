package com.mir00r.bloggingapp.service;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.models.UserBlog;
import com.mir00r.bloggingapp.repository.UserBlogRepository;
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
public class UserBlogService {
    private UserBlogRepository userBlogRepository;

    public List<UserBlog> findAll() {
        List<UserBlog> blogList = new ArrayList<>();
        blogList = userBlogRepository.findAll();
        return blogList;
    }

    public UserBlog findUserBlog(Long id) {
        return userBlogRepository.getOne(id);
    }

    public void save(UserBlog userBlog) {
        userBlogRepository.save(userBlog);
    }

    public void delete(Long id) {
        userBlogRepository.deleteById(id);
    }

    public List<UserBlog> findByBlog(Blog blog) {
        return userBlogRepository.findByBlog(blog);
    }

    public List<UserBlog> findByUser(User user) {
        return userBlogRepository.findByUser(user);
    }
}
