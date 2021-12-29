package com.microservice.playground.order.domain.service

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.repository.MessagePipelineAdaptor
import com.microservice.playground.order.domain.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository,
                   private val messagePipelineAdaptor: MessagePipelineAdaptor) {
    fun createOrder(order: Order) {
        orderRepository.saveOrder(order)
        messagePipelineAdaptor.publish("order", order)
    }
}