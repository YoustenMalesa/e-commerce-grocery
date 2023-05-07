package com.mrkenobii.ecommerceapp.dto.checkout;

import com.mrkenobii.ecommerceapp.dto.order.OrderDto;
import com.mrkenobii.ecommerceapp.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeResponse {
    private OrderDto order;
}
