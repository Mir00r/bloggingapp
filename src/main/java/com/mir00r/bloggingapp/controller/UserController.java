package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.service.RoleService;
import com.mir00r.bloggingapp.service.UserService;
import com.mir00r.bloggingapp.utils.Constant;
import com.mir00r.bloggingapp.validator.UserValidator;
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

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allUsers() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject(Constant.USERS, userService.findAllExceptCurrent(user.getId()));
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.setViewName("user");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/all-pending", method = RequestMethod.GET)
    public ModelAndView allPendingUsers() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject(Constant.USERS, userService.findAllByActive(0, user.getId()));
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.setViewName("user");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/all-approved", method = RequestMethod.GET)
    public ModelAndView allApprovedUsers() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject(Constant.USERS, userService.findAllByActive(1, user.getId()));
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.allMode.getName());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.setViewName("user");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveAdminUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User loggedUser = getUser();
        if (loggedUser.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            userValidator.validate(user, bindingResult);

            if (bindingResult.hasErrors()) {
                modelAndView.addObject("process", "ERROR");
                if (bindingResult.getFieldError().getField().equals("username")) {
                    modelAndView.addObject("pw_error", bindingResult.getFieldError().getDefaultMessage());
                } else {
                    modelAndView.addObject("pw_error", bindingResult.getFieldError().getDefaultMessage());
                }
                modelAndView.setViewName("user");
            } else {
                userService.saveUser(user, Constant.ROLE_TYPE.admin.getRoleId());
                modelAndView.setViewName("redirect:/users/all-pending");
            }
            //modelAndView.addObject("user", new User());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.newMode.getName());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/approved", method = RequestMethod.GET)
    public ModelAndView approvedUserStatus(@RequestParam Long id) {
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all-pending");
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            setModelAttribute(modelAndView, user);
            userService.updateUserActiveStatus(id, 1);
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/deactivate", method = RequestMethod.GET)
    public ModelAndView deactivateUserStatus(@RequestParam Long id) {
        User user = getUser();
        ModelAndView modelAndView = new ModelAndView("redirect:/users/all-approved");
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            setModelAttribute(modelAndView, user);
            userService.updateUserActiveStatus(id, 0);
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
        return modelAndView;
    }

    private void setModelAttribute(ModelAndView modelAndView, User user) {
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public ModelAndView detailsUser(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject("user", userService.findUser(id));
            modelAndView.addObject(Constant.ROLES, roleService.findAll());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.detailsMode.getName());
            setModelAttribute(modelAndView, user);
            modelAndView.setViewName("user");
        } else {
            modelAndView.setViewName("redirect:/access-denied");
        }
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

    @RequestMapping(value = "/new-admin", method = RequestMethod.GET)
    public ModelAndView newAdmin() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        if (user.getRole().getId() == Constant.ROLE_TYPE.admin.getRoleId()) {
            modelAndView.addObject("user", new User());
            modelAndView.addObject("users", userService.findAll());
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
            modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.newMode.getName());
            modelAndView.setViewName("user");
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
