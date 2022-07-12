package com.dev.core.auth.authservice.business;

import com.dev.core.auth.authservice.model.entity.Role;
import com.dev.core.auth.authservice.model.entity.User;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    User getUser(String username);

    List<User> getUsers();
}