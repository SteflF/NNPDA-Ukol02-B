package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.dto.ChangePasswordInputModel;
import io.javabrains.nnpda.model.db.User;
import io.javabrains.nnpda.model.dto.UserInputModel;
import io.javabrains.nnpda.repository.RoleRepository;
import io.javabrains.nnpda.repository.UserRepository;
import io.javabrains.nnpda.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    // called by authenticationManager.authenticate to get the user from database
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Override
    public User findOne(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));

        return user.orElse(null);
    }

    @Override
    public User save(UserInputModel user) {
        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword() == null ? null : bcryptEncoder.encode(user.getPassword()));
        newUser.setRole(roleRepository.findByName("USER").orElse(null));

        return userRepository.save(newUser);
    }

    @Override
    public Boolean changePassword(ChangePasswordInputModel inputModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails)auth.getPrincipal()).getUsername();

        if (!username.isEmpty()) {
            User user = userRepository.findByUsername(username);

            if ((user != null) && (user.getPassword().equals(bcryptEncoder.encode(inputModel.getOldPassword())))) {
                user.setPassword(inputModel.getNewPassword());
                userRepository.save(user);

                return true;
            }
        }

        return false;
    }

    @Override
    public Boolean alreadyRegistered(String username) {
        //User user = userRepository.findByUsername(username);

        return userRepository.findByUsername(username) != null;
    }
}
