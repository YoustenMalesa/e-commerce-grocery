package com.mrkenobii.ecommerceapp.dto.order;

import java.util.List;

public class OrderHistoryResponse {
    private List<OrderHistoryDto> history;

    public OrderHistoryResponse() {
    }

    public OrderHistoryResponse(List<OrderHistoryDto> history) {
        this.history = history;
    }

    public List<OrderHistoryDto> getHistory() {
        return history;
    }

    public void setHistory(List<OrderHistoryDto> history) {
        this.history = history;
    }
}
