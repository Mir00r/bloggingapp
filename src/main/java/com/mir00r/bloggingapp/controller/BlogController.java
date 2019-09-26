package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.service.BlogService;
import com.mir00r.bloggingapp.service.UserBlogService;
import com.mir00r.bloggingapp.service.UserService;
import com.mir00r.bloggingapp.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author mir00r on 2019-09-23
 * @project IntelliJ IDEA
 */
@Controller
@RequestMapping("/blogger/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserBlogService userBlogService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newBlog() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("blog", new Blog());
        modelAndView.addObject("blogs", blogService.findAll());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.newMode.getName());
        modelAndView.setViewName("blog");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveBlog(@Valid Blog blog, BindingResult bindingResult) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("redirect:/blogger/blogs/myblogs");
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        return modelAndView;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allBlogs() {
        ModelAndView modelAndView = new ModelAndView();
        if (getUser().getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
            modelAndView.addObject("blogs", blogService.findAll());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
            modelAndView.addObject(Constant.BLOG, Constant.BLOG_TYPE.all.getName());
            modelAndView.setViewName("blog");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/other-blog", method = RequestMethod.GET)
    public ModelAndView allOtherBlogs() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject("blogs", blogService.findAllBlogByUser(getUser(), Constant.BLOG_TYPE.other.getId()));
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
        modelAndView.addObject(Constant.BLOG, Constant.BLOG_TYPE.other.getName());
        modelAndView.setViewName("blog");
        return modelAndView;
    }

    @RequestMapping(value = "/myblogs", method = RequestMethod.GET)
    public ModelAndView allMyBlogs() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject("blogs", blogService.findAllBlogByUser(getUser(), Constant.BLOG_TYPE.mine.getId()));
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
        modelAndView.addObject(Constant.BLOG, Constant.BLOG_TYPE.mine.getName());
        modelAndView.setViewName("blog");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateBlog(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject("blog", blogService.findBlog(id));
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.updateMode.getName());
        modelAndView.setViewName("blog");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteBlog(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/blogger/blogs/myblogs");
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        blogService.delete(id);
        return modelAndView;
    }

    @RequestMapping(value = "/per_inf", method = RequestMethod.GET)
    public ModelAndView infoBlog(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject("blog", blogService.findBlog(id));
        modelAndView.addObject("userblogs", userBlogService.findByBlog(blogService.findBlog(id)));
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.infoMode.getName());
        modelAndView.setViewName("blog");
        return modelAndView;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView blogDetails(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject("blog", blogService.findBlog(id));
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.detailsMode.getName());
        modelAndView.setViewName("blog");
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }
}
