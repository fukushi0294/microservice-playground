package com.microservice.playground.order.domain.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean

@DynamoDbBean
data class OrderDetail (
    var itemId: String? = null,
    var retailerId: String? = null,
    var amount: Int? = null
)