package com.mrkenobii.ecommerceapp.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryDto {
    private List<OrderDetailsDto> details = new ArrayList<>();
    private String sessionId;
    private String userEmail;
    private String status;
}
