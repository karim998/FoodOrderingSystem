package com.TalabatSystem.Repositories;

import com.TalabatSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User , Long> {
    public User findByEmail(String username);
}
