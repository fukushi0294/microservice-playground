package com.microservice.playground.order.infrastructure.dto

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.model.OrderDetail
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
data class OrderEntity(
    private val orderId: String,
    private val customerId: String,
    private val orderDetails: List<OrderDetail>,
    private val status: String,
    private val orderedDate: Long
) {
    companion object {
        fun from(order: Order): OrderEntity {
            return OrderEntity(
                orderId = order.orderId,
                customerId = order.customerId,
                orderDetails = order.orderDetails,
                status = order.status.toString(),
                orderedDate = order.orderedDate
            )
        }
    }
    @DynamoDbPartitionKey
    fun getOrderId(): String {
        return this.orderId
    }

    @DynamoDbSortKey
    fun getCustomerId(): String {
        return this.customerId
    }

    @DynamoDbSortKey
    fun getOrderedDate(): Long {
        return this.orderedDate
    }
}