package com.example.BookMyRail2.service.impl.securityimpl;

import com.example.BookMyRail2.entity.User;
import com.example.BookMyRail2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> dbUser = userRepository.findByUsername(username);
        if(dbUser.isPresent())
        {
                var userObj = dbUser.get();
                return org.springframework.security.core.userdetails.User.builder()
                        .username(userObj.getUsername())
                        .password(userObj.getPassword())
                        .roles(getRoles(userObj))
                        .build();
        }
        else {
            throw new UsernameNotFoundException(username+ " not found ");
        }
    }


    private String[] getRoles(User user)
    {
       if(user.getRoles()==null)
       {
           return new String[] {"USER"};
       }
       return user.getRoles().split(",");
    }


}
