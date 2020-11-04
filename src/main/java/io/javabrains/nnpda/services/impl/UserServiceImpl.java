package io.javabrains.nnpda.services.impl;

import io.javabrains.nnpda.model.ChangePasswordInputModel;
import io.javabrains.nnpda.model.User;
import io.javabrains.nnpda.model.UserInputModel;
import io.javabrains.nnpda.repository.UserRepository;
import io.javabrains.nnpda.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // called by authenticationManager.authenticate to get the user from database
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public User findOne(String userName) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUserName(userName));

        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User save(UserInputModel user) {
        User newUser = new User();

        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword() == null ? null : bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public Boolean changePassword(ChangePasswordInputModel inputModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var username = ((UserDetails)auth.getPrincipal()).getUsername();

        if (!username.isEmpty()) {
            User user = userRepository.findByUserName(username);

            if ((user != null) && (user.getPassword().equals(bCryptPasswordEncoder.encode(inputModel.getOldPassword())))) {
                user.setPassword(inputModel.getNewPassword());
                userRepository.save(user);

                return true;
            }
        }

        return false;
    }
}
