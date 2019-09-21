package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.Role;
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
@RequestMapping("/admin/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newRole() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("role", new Role());
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject("mode", "MODE_NEW");
        modelAndView.setViewName("role");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveRole(@Valid Role role, BindingResult bindingResult) {
        roleService.save(role);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/roles/all");
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        return modelAndView;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView allRoles() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new Role());
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject("mode", "MODE_ALL");
        modelAndView.setViewName("role");
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateRole(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("personel_type", new Role());
        modelAndView.addObject("role", roleService.findRole(id));
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        modelAndView.addObject("mode", "MODE_UPDATE");
        modelAndView.setViewName("role");
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteRole(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/roles/all");
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), getUser());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), getUser().getRole().getName());
        roleService.delete(id);
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        return user;
    }
}
