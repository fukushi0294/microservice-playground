package com.microservice.playground.order.domain.model

import org.springframework.data.cassandra.core.mapping.Table
import java.time.LocalDateTime

@Table
data class Order(
    val orderId: String,
    val customerId: String,
    val storeId: String,
    val itemId: List<String>,
    val status: String,
    val orderedDate: LocalDateTime,
)