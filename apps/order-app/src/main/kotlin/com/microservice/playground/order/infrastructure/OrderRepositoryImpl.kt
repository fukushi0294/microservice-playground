package com.microservice.playground.order.infrastructure

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.repository.OrderRepository
import com.microservice.playground.order.infrastructure.dto.OrderEntity
import com.microservice.playground.order.infrastructure.repository.OrderEntityRepository
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(private val orderEntityRepository: OrderEntityRepository) : OrderRepository {
    override fun saveOrder(order: Order) {
        val entity = OrderEntity.from(order)
        orderEntityRepository.save(entity)
    }

    override fun getOrders(customerId: String): List<Order> {
        return orderEntityRepository.findByCustomerId(customerId).map {
            Order(
                orderId = it.orderId,
                customerId = it.customerId,
                storeId = it.storeId,
                itemId = it.itemIds,
                status = it.status,
                orderedDate = it.orderedDate
            )
        }
    }
}