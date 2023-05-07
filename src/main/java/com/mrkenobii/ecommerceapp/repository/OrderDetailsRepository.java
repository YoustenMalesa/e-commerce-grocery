package com.mrkenobii.ecommerceapp.repository;

import com.mrkenobii.ecommerceapp.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findAllByOrderId(Integer id);
}
