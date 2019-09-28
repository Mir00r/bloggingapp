package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.service.BlogService;
import com.mir00r.bloggingapp.service.CommentBlogService;
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
    private CommentBlogService commentBlogService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newBlog() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            modelAndView.addObject("blog", new Blog());
            modelAndView.addObject("blogs", blogService.findAll());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.newMode.getName());
            modelAndView.setViewName("blog");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveBlog(@Valid Blog blog, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/blogger/blogs/myblogs");
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            blogService.save(blog);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allBlogs() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject("blogs", blogService.findAll());
            modelAndView.addObject("commentBlogs", commentBlogService.findAllByBlogAndUser());
            setModelAttribute(modelAndView, user);
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/pending-blog", method = RequestMethod.GET)
    public ModelAndView allPendingBlogs() {
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject("blogs", blogService.findAllBlogsByStatus(0));
            modelAndView.addObject("commentBlogs", commentBlogService.findAllByBlogAndUser());
            setModelAttribute(modelAndView, user);
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/approved-blog", method = RequestMethod.GET)
    public ModelAndView allApprovedBlogs() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject("blogs", blogService.findAllBlogsByStatus(1));
            modelAndView.addObject("commentBlogs", commentBlogService.findAllByBlogAndUser());
            setModelAttribute(modelAndView, user);
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    private void setModelAttribute(ModelAndView modelAndView, User user) {
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
        modelAndView.addObject(Constant.BLOG, Constant.BLOG_TYPE.all.getName());
        modelAndView.setViewName("blog");
    }

    @RequestMapping(value = "/other-blog", method = RequestMethod.GET)
    public ModelAndView allOtherBlogs() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
            modelAndView.addObject("blogs", blogService.findAllBlogByUser(user, Constant.BLOG_TYPE.other.getId()));
            modelAndView.addObject("commentBlogs", commentBlogService.findAllByBlogAndUser());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
            modelAndView.addObject(Constant.BLOG, Constant.BLOG_TYPE.other.getName());
            modelAndView.setViewName("blog");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/myblogs", method = RequestMethod.GET)
    public ModelAndView allMyBlogs() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
            modelAndView.addObject("blogs", blogService.findAllBlogByUser(user, Constant.BLOG_TYPE.mine.getId()));
            modelAndView.addObject("commentBlogs", commentBlogService.findAllByBlogAndUser());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
            modelAndView.addObject(Constant.BLOG, Constant.BLOG_TYPE.mine.getName());
            modelAndView.setViewName("blog");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateBlog(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
            modelAndView.addObject("blog", blogService.findBlog(id));
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.updateMode.getName());
            modelAndView.setViewName("blog");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteBlog(@RequestParam Long id) {
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            modelAndView.setViewName("redirect:/blogger/blogs/myblogs");
        } else {
            modelAndView.setViewName("redirect:/blogger/blogs/all");
        }
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        blogService.delete(id);
        return modelAndView;
    }

    @RequestMapping(value = "/approved", method = RequestMethod.GET)
    public ModelAndView approvedBlog(@RequestParam Long id) {
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView("redirect:/blogger/blogs/pending-blog");
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            setAttribute(modelAndView, user);
            blogService.updateBlogStatus(id, 1);
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.GET)
    public ModelAndView deactivateBlog(@RequestParam Long id) {
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView("redirect:/blogger/blogs/approved-blog");
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            setAttribute(modelAndView, user);
            blogService.updateBlogStatus(id, 0);
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    private void setAttribute(ModelAndView modelAndView, User user) {
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
    }

    @RequestMapping(value = "/per_inf", method = RequestMethod.GET)
    public ModelAndView infoBlog(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
        modelAndView.addObject("blog", blogService.findBlog(id));
        modelAndView.addObject("userblogs", commentBlogService.findByBlog(blogService.findBlog(id)));
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
        modelAndView.addObject("comments", commentBlogService.findByBlog(blogService.findBlog(id)));
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
