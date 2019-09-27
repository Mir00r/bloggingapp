package com.mir00r.bloggingapp.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author mir00r on 2019-09-20
 * @project IntelliJ IDEA
 */
@Entity
@Table(name = "user")
public class User extends BaseModel implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String username;
    private String avatar;

    private String email;

    private String password;
    private String name;

    private int active;

    private boolean enabled;
    private boolean isExpired;
    private boolean isLocked;
    private boolean isCredentialsExpired;
    private boolean isSuperAdmin;

//    @ManyToMany(fetch = FetchType.EAGER)    // FetchType.EAGER: don't use lazy load.
//    @JoinTable(name = "core_user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles;

    @ManyToOne
    private Role role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        //for (Role role : this.roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorityList.add(authority);
        //}
        return authorityList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.isExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.isCredentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isCredentialsExpired() {
        return isCredentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        isCredentialsExpired = credentialsExpired;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
