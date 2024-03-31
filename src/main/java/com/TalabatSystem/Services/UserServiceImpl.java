package com.TalabatSystem.Services;

import com.TalabatSystem.Models.User;
import com.TalabatSystem.Repositories.UserRepo;
import com.TalabatSystem.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);

        if(user==null){
            throw new Exception("USer Not Found");
        }

        return user;
    }
}
