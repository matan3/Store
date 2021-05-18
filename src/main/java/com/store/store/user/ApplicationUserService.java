package com.store.store.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.store.store.security.ApplicationUserRole.CUSTOMER;
import static com.store.store.security.ApplicationUserRole.OWNER;

@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ApplicationUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        user.orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username $s not found",username)));
        return createApplicationUserFromUser(user);
    }

    private ApplicationUser createApplicationUserFromUser(Optional<User> user){
        ApplicationUser applicationUser = user.map(ApplicationUser::new).get();
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        if(applicationUser.getIsOwner())
            applicationUser.setAuthorities(OWNER.getGrantedAuthorities());
        else
            applicationUser.setAuthorities(CUSTOMER.getGrantedAuthorities());
        return applicationUser;
    }

}
