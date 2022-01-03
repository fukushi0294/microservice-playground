package com.microservice.playground.order.domain.service

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.model.OrderStatus
import com.microservice.playground.order.domain.repository.OrderRepository
import com.microservice.playground.order.infrastructure.repository.EventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class OrderService(private val orderRepository: OrderRepository,
                   private val eventPublisher: EventPublisher) {
    fun createOrder(order: Order) {
        orderRepository.saveOrder(order)
        eventPublisher.publish(OrderStatus.ORDER_REQUEST.toString(), order)
    }
}