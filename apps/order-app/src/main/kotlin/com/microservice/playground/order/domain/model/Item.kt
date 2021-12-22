package com.microservice.playground.order.domain.model

import org.springframework.data.cassandra.core.mapping.Table

@Table
data class Item(
    val itemId: String,
    val name: String
)