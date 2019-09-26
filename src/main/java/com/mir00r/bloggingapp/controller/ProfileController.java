package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.service.UserBlogService;
import com.mir00r.bloggingapp.service.UserService;
import com.mir00r.bloggingapp.utils.Constant;
import com.mir00r.bloggingapp.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author mir00r on 2019-09-26
 * @project IntelliJ IDEA
 */
@Controller
@RequestMapping("/myprofile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value = "/inf", method = RequestMethod.GET)
    public ModelAndView showProfile() {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject("user", userService.findUser(user.getId()));
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.infoMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());//Authentication for NavBar
        modelAndView.setViewName("user_profile");
        return modelAndView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveProfile(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/myprofile/inf");
        userService.save(user);
        User updatedUser = getUser();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), updatedUser);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), updatedUser.getRole().getName());
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView updateProfile(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject("user", userService.findUser(id));
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.editMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        modelAndView.setViewName("user_profile");
        return modelAndView;
    }

    //--------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/savepassword_change", method = RequestMethod.POST)
    public ModelAndView confirmPasswordChange(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        passwordValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("process", "ERROR");
            modelAndView.addObject("pw_error", "Error : Check your old or new password!");
            modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
            modelAndView.setViewName("user_profile");
        } else {
            userService.updatePasswordInfo(user);
            modelAndView.addObject("process", "SUCCESS");
            modelAndView.addObject("pw_success", "Well done! You successfully change your password.");
            modelAndView.setViewName("user_profile");
        }
        User updatedUser = getUser();
        modelAndView.addObject("user", userService.findUser(user.getId()));
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.passMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), updatedUser);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), updatedUser.getRole().getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        return modelAndView;
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.GET)
    public ModelAndView changePassword(@RequestParam Long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = getUser();
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.rule.name(), new User());
        modelAndView.addObject("user", userService.findUser(id));
        modelAndView.addObject(Constant.MODE, Constant.ACTION_MODE.passMode.getName());
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.auth.name(), user);
        modelAndView.addObject(Constant.ATTRIBUTE_NAME.control.name(), user.getRole().getName());
        modelAndView.setViewName("user_profile");
        return modelAndView;
    }

    private User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        return user;
    }
}
