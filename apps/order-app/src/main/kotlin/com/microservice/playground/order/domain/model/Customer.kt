package com.microservice.playground.order.domain.model

import java.time.LocalDateTime

data class Customer (
    val customerId: String,
    val registeredAt: LocalDateTime
)