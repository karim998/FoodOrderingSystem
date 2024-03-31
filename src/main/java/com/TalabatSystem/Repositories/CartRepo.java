package com.TalabatSystem.Repositories;

import com.TalabatSystem.Models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart , Long> {

    public Cart findByCustomerId(Long userId);
}
