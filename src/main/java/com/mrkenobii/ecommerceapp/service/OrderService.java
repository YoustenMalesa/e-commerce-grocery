package com.mrkenobii.ecommerceapp.service;

import com.mrkenobii.ecommerceapp.dto.checkout.CheckoutItemDto;
import com.mrkenobii.ecommerceapp.dto.order.OrderDetailsDto;
import com.mrkenobii.ecommerceapp.dto.order.OrderDto;
import com.mrkenobii.ecommerceapp.dto.order.OrderHistoryDto;
import com.mrkenobii.ecommerceapp.exception.CustomException;
import com.mrkenobii.ecommerceapp.model.Order;
import com.mrkenobii.ecommerceapp.model.OrderDetails;
import com.mrkenobii.ecommerceapp.repository.OrderDetailsRepository;
import com.mrkenobii.ecommerceapp.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final AuthenticationService authenticationService;
    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;
    @Value("${BASE_URL}")
    private String baseUrl;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public List<OrderHistoryDto> findOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findAllByUserEmail(email);
        System.out.println("Number of orders: " + orders.size());
        if(orders == null || orders.isEmpty()) {
            throw new CustomException("No order details found.");
        }

        List<OrderHistoryDto> history = new ArrayList<>();


        orders.stream().forEach( od -> {
            OrderHistoryDto historyDto = new OrderHistoryDto();
            System.out.println("Streaming for order: " + od.getSessionId());
            historyDto.setStatus(od.getStatus());
            historyDto.setUserEmail(od.getUserEmail());
            historyDto.setSessionId(od.getSessionId());

            List<OrderDetails> orderDetails = orderDetailsRepository.findAllByOrderId(od.getId());
            System.out.println("Number of order details:: " + orderDetails.size());
            List<OrderDetailsDto> productDetails = new ArrayList<>();

            orderDetails.forEach(detail -> {
                OrderDetailsDto dto = new OrderDetailsDto();
                dto.setQuantity(detail.getQuantity());
                dto.setProductId(detail.getProductId());
                dto.setId(detail.getId());
                dto.setOrderId(od.getId());

                productDetails.add(dto);
            });
            historyDto.setDetails(productDetails);
            history.add(historyDto);
        });


        return history;
    }

    public void saveOrder(OrderDto order) {
        Order od = new Order();
        od.setUserEmail(order.getUserEmail());
        od.setStatus("RECEIVED");
        od.setSessionId(order.getSessionId());

        od = orderRepository.saveAndFlush(od);
        final int id = od.getId();

        order.getCheckoutItemDtoList().forEach(c -> {
            OrderDetails d = new OrderDetails();
            d.setProductId(c.getProductId().intValue());
            d.setOrderId(id);
            d.setQuantity(c.getQuantity());

            orderDetailsRepository.saveAndFlush(d);
        });
    }

    public Session createSession(OrderDto orderDto) throws StripeException {
        String successUrl = baseUrl + "payment/success";
        String failureUrl = baseUrl + "payment/failed";
        Stripe.apiKey = apiKey;
        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();
        for(CheckoutItemDto checkoutItemDto: orderDto.getCheckoutItemDtoList())
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureUrl)
                .setSuccessUrl(successUrl)
                .addAllLineItem(sessionItemList)
                .build();
        return Session.create(params);
    }

    private SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem
                .builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData
                .builder()
                .setCurrency("ZAR")
                .setUnitAmount((checkoutItemDto.getPrice().longValue()) * 100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()
                )
                .build();
    }
}
