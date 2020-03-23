package me.kingcjy.demo.api;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.kingcjy.demo.domain.Address;
import me.kingcjy.demo.domain.Order;
import me.kingcjy.demo.domain.OrderStatus;
import me.kingcjy.demo.repository.OrderRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;


    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
        }
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<SimpleOrderDto> orders = orderRepository.findAll().stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return orders;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<SimpleOrderDto> orders = orderRepository.findAllWithMemberDelivery().stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
        return orders;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }


    @Data
    public static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

}
