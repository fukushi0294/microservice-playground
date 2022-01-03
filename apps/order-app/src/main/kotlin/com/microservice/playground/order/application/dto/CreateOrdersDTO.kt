package com.microservice.playground.order.application.dto

import com.microservice.playground.order.domain.model.OrderDetail

data class CreateOrdersDTO (
    val orderDetails: List<OrderDetail>
)
