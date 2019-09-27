package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.Blog;
import com.mir00r.bloggingapp.models.Role;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.service.BlogService;
import com.mir00r.bloggingapp.service.RoleService;
import com.mir00r.bloggingapp.service.UserService;
import com.mir00r.bloggingapp.utils.Constant;
import com.mir00r.bloggingapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mir00r on 2019-09-20
 * @project IntelliJ IDEA
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user, Constant.ROLE_TYPE.blogger.getRoleId());
            modelAndView.addObject("successMessage", "Registration successful please activate this account from admin user.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("403");
        return modelAndView;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Role role = new Role();
        Role role2 = new Role();
        role = roleService.findRole(Constant.ROLE_TYPE.admin.getRoleName());
        role2 = roleService.findRole(Constant.ROLE_TYPE.blogger.getRoleName());
        List<User> users = new ArrayList<>();
        List<User> users2 = new ArrayList<>();
        users = userService.findUserbyRole(role);
        users2 = userService.findUserbyRole(role2);
        List<Blog> blogs = new ArrayList<>();
        blogs = blogService.findAll();
        int blogCount = blogs.size();
        int adminCount = users.size();
        int userCount = users2.size();
        modelAndView.addObject("adminCount", adminCount);//Authentication for NavBar
        modelAndView.addObject("userCount", userCount);//Authentication for NavBar
        modelAndView.addObject("blogCount", blogCount);//Authentication for NavBar*/
        //-----------------------------------------
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User loginUser = userService.findUserByEmail(auth.getName());
        User loginUser = userService.findByUsername(auth.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.getName(), loginUser.getRole().getName());//Authentication for NavBar
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.getName(), loginUser);
        //List<UserTask> userTasks = new ArrayList<>();
        //userTasks = userTaskService.findByUser(loginUser);
        //modelAndView.addObject("userTaskSize", userTasks.size());
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
