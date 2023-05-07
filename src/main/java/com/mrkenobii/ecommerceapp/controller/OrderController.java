package com.mrkenobii.ecommerceapp.controller;

import com.mrkenobii.ecommerceapp.dto.checkout.CheckoutItemDto;
import com.mrkenobii.ecommerceapp.dto.checkout.StripeResponse;
import com.mrkenobii.ecommerceapp.dto.order.OrderDto;
import com.mrkenobii.ecommerceapp.model.Order;
import com.mrkenobii.ecommerceapp.model.OrderDetails;
import com.mrkenobii.ecommerceapp.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody OrderDto orderDto) throws StripeException {
        Session session = orderService.createSession(orderDto);
        orderDto.setSessionId(session.getId());


        orderService.saveOrder(orderDto);
        StripeResponse stripeResponse = new StripeResponse(orderDto);

        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findOrdersByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(orderService.findOrdersByEmail(email), HttpStatus.OK);
    }
}
