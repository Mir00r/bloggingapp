package com.mir00r.bloggingapp.validator;

import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author mir00r on 2019-09-26
 * @project IntelliJ IDEA
 */
@Component
public class PasswordValidator implements Validator {
    @Autowired
    UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        String oldPassword = userService.findUser(user.getId()).getPassword();
        String postOldPassword = user.getName();
        String newPassword = user.getPassword();
        String confirmPassword = user.getAvatar();

        if (!passwordEncoder.matches(postOldPassword, oldPassword)) {
            errors.rejectValue("name", "error.user",
                    "Error : Check your old password!");
        } else if (!newPassword.equals(confirmPassword)) {
            errors.rejectValue("password", "error.user",
                    "Error : Check your new or confirm password!");
        }
    }
}
