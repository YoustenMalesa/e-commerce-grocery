package com.mrkenobii.ecommerceapp.dto.user;

import com.mrkenobii.ecommerceapp.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Address billingAddress;
    private Address shippingAddress;
}
