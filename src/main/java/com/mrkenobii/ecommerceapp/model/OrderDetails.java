package com.mrkenobii.ecommerceapp.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "order_id")
    private int orderId;

}
