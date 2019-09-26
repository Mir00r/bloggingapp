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
        User user = getUser();
        modelAndView.addObject(Constant.USERS, userService.findAll());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/all-pending", method = RequestMethod.GET)
    public ModelAndView allPendingUsers() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        modelAndView.addObject(Constant.USERS, userService.findAllByActive(0, user.getId()));
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/all-approved", method = RequestMethod.GET)
    public ModelAndView allApprovedUsers() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        modelAndView.addObject(Constant.USERS, userService.findAllByActive(1, user.getId()));
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all-pending");
        user.setPassword(userService.findUser(user.getId()).getPassword());
        user.setActive(userService.findUser(user.getId()).getActive());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        userService.save(user);
        return modelAndView;
    }

    @RequestMapping(value = "/approved", method = RequestMethod.GET)
    public ModelAndView approvedUserStatus(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all-pending");
        setModelAttribute(modelAndView);
        userService.updateUserActiveStatus(id, 1);
        return modelAndView;
    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.GET)
    public ModelAndView deactivateUserStatus(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all-approved");
        setModelAttribute(modelAndView);
        userService.updateUserActiveStatus(id, 0);
        return modelAndView;
    }

    private void setModelAttribute(ModelAndView modelAndView) {
        User user = getUser();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateUser(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject("user", userService.findUser(id));
        modelAndView.addObject(Constant.ROLES, roleService.findAll());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.updateMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView detailsUser(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject("user", userService.findUser(id));
        modelAndView.addObject(Constant.ROLES, roleService.findAll());
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.detailsMode.getName());
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
        User user = userService.findByUsername(auth.getName());
        return user;
    }
}
