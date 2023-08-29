package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.UserRole;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;


    //*Not: resgiseter()
    public void saveUser(UserRequest userRequest) {

        User myUser = new User();
        myUser.setFirstName(userRequest.getFirstName());
        myUser.setLastName(userRequest.getLastName());
        myUser.setUserName(userRequest.getUserName());
//        myUser.setPassword(userRequest.getPassword());

        String password = userRequest.getPassword();//hale plaintext encode etmemiz lazım

        String encodedPassword = passwordEncoder.encode(password);//BYCrypt ile encode ettik

        myUser.setPassword(encodedPassword);

        Role role =roleService.getRoleByType(UserRole.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        myUser.setRoles(roles); //* user a admin rolü atadık
        //*

        userRepository.save(myUser);


    }
}
