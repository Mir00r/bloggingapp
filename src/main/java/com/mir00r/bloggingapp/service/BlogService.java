package com.mir00r.bloggingapp.service;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.repository.BlogRepository;
import com.mir00r.bloggingapp.repository.UserRepository;
import com.mir00r.bloggingapp.utils.Constant;
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
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Blog> findAll() {
        List<Blog> blogList = new ArrayList<>();
        blogList = blogRepository.findAll();
        return blogList;
    }

    public List<Blog> findAllBlogByUser(User user, long blogTypeId) {
        List<Blog> userBlogList = new ArrayList<>();
        if (blogTypeId == Constant.BLOG_TYPE.mine.getId())
            userBlogList = blogRepository.findBlogByUser(user);
        else userBlogList = blogRepository.findOtherBlog(user.getId());
        return userBlogList;
    }

    public Blog findBlog(Long id) {
        return blogRepository.getOne(id);
    }

    public void save(Blog blog) {
        blog.setUser(getUser());
        blogRepository.save(blog);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }
}
