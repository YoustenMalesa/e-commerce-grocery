package com.mrkenobii.ecommerceapp.dto.order;

import com.mrkenobii.ecommerceapp.dto.checkout.CheckoutItemDto;
import com.mrkenobii.ecommerceapp.model.Order;
import com.mrkenobii.ecommerceapp.model.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private List<CheckoutItemDto> checkoutItemDtoList;
    private Set<OrderDetailsDto> details = new HashSet<>();
    private String sessionId;
    private String userEmail;
    private String status;
}
