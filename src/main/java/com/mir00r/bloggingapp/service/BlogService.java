package com.mir00r.bloggingapp.service;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Blog> findAll() {
        List<Blog> blogList = new ArrayList<>();
        blogList = blogRepository.findAll();
        return blogList;
    }

    public Blog findBlog(Long id) {
        return blogRepository.getOne(id);
    }

    public void save(Blog task) {
        blogRepository.save(task);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
