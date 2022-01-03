package com.microservice.playground.order.domain.model

data class OrderDetail (
    val itemId: String,
    val retailerId: String,
    val amount: Int
)