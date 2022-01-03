package com.microservice.playground.order.domain.model

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

data class Order(
    val orderId: String,
    val customerId: String,
    val orderDetails: List<OrderDetail>,
    val orderedDate: Long,
    var status: OrderStatus,
) {
    companion object {
        fun create(customerId: String, orderDetails: List<OrderDetail>): Order {
            return Order(
                orderId = UUID.randomUUID().toString(),
                customerId,
                orderDetails,
                orderedDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                status = OrderStatus.ORDER_REQUEST
            )
        }
    }
}