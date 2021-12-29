package com.microservice.playground.order.infrastructure.dto

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import com.google.cloud.spring.data.datastore.core.mapping.Field
import com.microservice.playground.order.domain.model.Order
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

@Entity(name = "orders")
data class OrderEntity (
    @Id
    @Field(name = "order_id")
    val orderId: String,
    @Field(name = "customer_id")
    val customerId: String,
    @Field(name = "store_id")
    val storeId: String,
    @Field(name = "item_ids")
    val itemIds: List<String>,
    val status: String,
    val orderedDate: LocalDateTime,
) {
    companion object {
        fun from(order: Order) : OrderEntity {
            return OrderEntity(
                orderId = order.orderId,
                customerId = order.customerId,
                storeId = order.storeId,
                itemIds = order.itemId,
                status = order.status,
                orderedDate = order.orderedDate
            )
        }
    }
}