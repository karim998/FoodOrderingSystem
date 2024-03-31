package com.TalabatSystem.Repositories;

import com.TalabatSystem.Models.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<Orderitem, Long> {
}
