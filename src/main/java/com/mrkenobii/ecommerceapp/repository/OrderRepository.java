package com.mrkenobii.ecommerceapp.repository;

import com.mrkenobii.ecommerceapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserEmail(String email);
}
