package com.mir00r.bloggingapp.service;

import com.mir00r.bloggingapp.models.Role;
import com.mir00r.bloggingapp.models.User;
import com.mir00r.bloggingapp.repository.RoleRepository;
import com.mir00r.bloggingapp.repository.UserRepository;
import com.mir00r.bloggingapp.utils.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mir00r on 2019-09-21
 * @project IntelliJ IDEA
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BlogService blogService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        users = userRepository.findAll();
        return users;
    }

    public List<User> findAllExceptCurrent(Long userId) {
        List<User> users = new ArrayList<>();
        users = userRepository.findAllExcept(userId);
        return users;
    }

    public List<User> findAllByActive(int active, Long userId) {
        List<User> users = new ArrayList<>();
        users = userRepository.findAllUser(active, userId);
        return users;
    }

    public User findUser(Long id) {
        return userRepository.getOne(id);
    }

    public void delete(Long id) {
        blogService.delete(blogService.findAllBlogByUser(findUser(id)));
        userRepository.deleteById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public void updateUserActiveStatus(Long id, int activate) {
        User user = findUser(id);
        user.setActive(activate);
        userRepository.save(user);
    }

    public void updatePasswordInfo(User user) {
        User oldUser = findUser(user.getId());
        user.setName(oldUser.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveUser(User user, Long roleId) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (roleId == Constant.ROLE_TYPE.admin.getRoleId()) user.setActive(1);
        Role userRole = getRole(roleId);
        user.setRole(userRole);
        userRepository.save(user);
    }

    private Role getRole(Long id) {
        if (id == Constant.ROLE_TYPE.blogger.getRoleId())
            return roleRepository.findByName(Constant.ROLE_TYPE.blogger.getRoleName());
        else
            return roleRepository.findByName(Constant.ROLE_TYPE.admin.getRoleName());
    }

    public List<User> findUserbyRole(Role role) {
        return userRepository.findByRole(role);
    }
}
