package com.microservice.playground.order.domain.model

data class Order(
    val orderId: String,
    val customerId: String,
    val orderDetails: List<OrderDetail>,
    val orderedDate: Long,
    var status: OrderStatus,
)