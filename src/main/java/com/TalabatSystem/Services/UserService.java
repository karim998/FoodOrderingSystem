package com.TalabatSystem.Services;

import com.TalabatSystem.Models.User;

public interface UserService {
    public User findUserByJwtToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
