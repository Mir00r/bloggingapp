package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.User;
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
 * @author mir00r on 2019-09-21
 * @project IntelliJ IDEA
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.USERS, userService.findAll());
        modelAndView.addObject("mode", "MODE_ALL");
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all");
        user.setPassword(userService.findUser(user.getId()).getPassword());
        user.setActive(userService.findUser(user.getId()).getActive());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        userService.save(user);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateUser(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject("user", userService.findUser(id));
        modelAndView.addObject(Constant.ROLES, roleService.findAll());
        modelAndView.addObject("mode", "MODE_UPDATE");
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteUser(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all");
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        userService.delete(id);
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }
}
