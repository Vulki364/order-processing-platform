package com.example.orderservice.service;

import com.example.orderservice.dto.CreateOrderRequest;
import com.example.orderservice.event.OrderCreatedEvent;
import com.example.orderservice.kafka.OrderEventProducer;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order(
                request.getProductId(),
                request.getQuantity(),
                "NEW"
        );

        Order savedOrder = orderRepository.save(order);

        orderEventProducer.sendOrderCreatedEvent(
                new OrderCreatedEvent(
                        savedOrder.getId(),
                        savedOrder.getProductId(),
                        savedOrder.getQuantity()
                )
        );

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
