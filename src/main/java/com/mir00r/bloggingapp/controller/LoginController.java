package com.mir00r.bloggingapp.controller;

import com.mir00r.bloggingapp.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author mir00r on 2019-09-20
 * @project IntelliJ IDEA
 */
@Controller
public class LoginController {

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
//        User user = new User();
//        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
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
        /*Role role = new Role();
        Role role2 = new Role();
        role = roleService.findRole("ADMIN");
        role2 = roleService.findRole("USER");
        List<User> users = new ArrayList<>();
        List<User> users2 = new ArrayList<>();
        users = userService.findUserbyRole(role);
        users2 = userService.findUserbyRole(role2);
        List<Task> tasks = new ArrayList<>();
        tasks = taskService.findAll();
        int taskCount = tasks.size();
        int adminCount = users.size();
        int userCount = users2.size();
        modelAndView.addObject("adminCount", adminCount);//Authentication for NavBar
        modelAndView.addObject("userCount", userCount);//Authentication for NavBar
        modelAndView.addObject("taskCount", taskCount);//Authentication for NavBar*/
        //-----------------------------------------
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User loginUser = userService.findUserByEmail(auth.getName());
        User loginUser = new User();
        modelAndView.addObject("control", "USER");//Authentication for NavBar
        modelAndView.addObject("auth", loginUser);
        //List<UserTask> userTasks = new ArrayList<>();
        //userTasks = userTaskService.findByUser(loginUser);
        //modelAndView.addObject("userTaskSize", userTasks.size());
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
