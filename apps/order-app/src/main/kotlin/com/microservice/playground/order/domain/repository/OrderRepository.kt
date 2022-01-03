package com.microservice.playground.order.domain.repository

import com.microservice.playground.order.domain.model.Order

interface OrderRepository {
    fun saveOrder(order: Order)
    fun getOrders(customerId: String): List<Order>
    fun deleteOrder(customerId: String)
}