package com.hdev.notification_service.service;

import com.hdev.notification_service.dto.OrderDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumeOrder(OrderDTO orderDTO){
        // exemplo de consumidor
        System.out.println("Recebendo pedido: " + orderDTO);
    }
}
