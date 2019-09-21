package com.mir00r.bloggingapp.service;

import com.mir00r.bloggingapp.models.Role;
import com.mir00r.bloggingapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        roles = roleRepository.findAll();
        return roles;
    }

    public Role findRole(Long id) {
        return roleRepository.getOne(id);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);

    }

    public Role findRole(String role) {
        // TODO Auto-generated method stub
        return roleRepository.findByName(role);
    }
}
