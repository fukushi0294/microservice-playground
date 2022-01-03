package com.microservice.playground.order.infrastructure

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.model.OrderDetail
import com.microservice.playground.order.domain.model.OrderStatus
import com.microservice.playground.order.infrastructure.repository.OrderRepositoryImpl
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest(classes = [OrderRepositoryImplTest::class, OrderRepositoryImpl::class])
@TestMethodOrder(MethodOrderer.MethodName::class)
class OrderRepositoryImplTest {
    @Autowired
    private lateinit var target: OrderRepositoryImpl
    companion object {
        val newlyCreatedItemKey = UUID.randomUUID().toString()
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    fun newOrderShouldBeCreated() {
        val order = Order.create(
            customerId = newlyCreatedItemKey,
            orderDetails = mutableListOf(
                OrderDetail(
                    itemId = UUID.randomUUID().toString(),
                    retailerId = UUID.randomUUID().toString(),
                    amount = 1
                )
            )
        )
        target.saveOrder(order)
        val orders = target.getOrders(newlyCreatedItemKey)
        assertTrue(orders.size == 1)
        assertTrue(orders[0].status == OrderStatus.ORDER_REQUEST)
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    fun cleanUp() {
        target.deleteOrder(customerId = newlyCreatedItemKey)
    }
}