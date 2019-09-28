package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.CommentBlog;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.service.BlogService;
import com.mir00r.bloggingapp.service.CommentBlogService;
import com.mir00r.bloggingapp.service.RoleService;
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
 * @author mir00r on 2019-09-28
 * @project IntelliJ IDEA
 */
@Controller
@RequestMapping("/blog/comment")
public class CommentBlogController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentBlogService commentBlogService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newBlogComment(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Blog());
            modelAndView.addObject("blog", blogService.findBlog(id));
            modelAndView.addObject("commentBlog", new CommentBlog());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.commentMode.getName());
            modelAndView.setViewName("blog");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveCommentBlog(@Valid CommentBlog commentBlog, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/blogger/blogs/other-blog");
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.blogger.getRoleId()) {
            commentBlogService.save(commentBlog);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }
}
