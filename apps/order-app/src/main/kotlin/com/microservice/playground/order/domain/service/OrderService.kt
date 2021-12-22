package com.microservice.playground.order.domain.service

import com.microservice.playground.order.domain.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(private val orderRepository: OrderRepository) {
}