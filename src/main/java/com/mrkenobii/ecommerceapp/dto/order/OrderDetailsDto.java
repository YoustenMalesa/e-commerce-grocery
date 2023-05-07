package com.mrkenobii.ecommerceapp.dto.order;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private int id;
    private int productId;
    private int quantity;
    private int orderId;
}
