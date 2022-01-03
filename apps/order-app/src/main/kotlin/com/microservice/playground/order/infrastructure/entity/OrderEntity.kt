package com.microservice.playground.order.infrastructure.entity

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.model.OrderDetail
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*

@DynamoDbBean
data class OrderEntity(
    @get:DynamoDbSecondaryPartitionKey(indexNames = ["order_by_id"]) @get:DynamoDbAttribute("order_id")
    var orderId: String? = null,
    @get:DynamoDbPartitionKey @get:DynamoDbAttribute("customer_id")
    var customerId: String? = null,
    var orderDetails: List<OrderDetail> = emptyList(),
    var status: String? = null,
    @get:DynamoDbSortKey @get:DynamoDbAttribute("ordered_date")
    var orderedDate: Long? = null
) {
    companion object {
        fun from(order: Order): OrderEntity {
            return OrderEntity(
                customerId = order.customerId,
                orderId = order.orderId,
                orderDetails = order.orderDetails,
                status = order.status.toString(),
                orderedDate = order.orderedDate
            )
        }
    }

    fun toOrder(): Order {
        return Order.create(
            customerId = this.customerId!!,
            orderDetails = this.orderDetails
        )
    }
}