package com.microservice.playground.order.application

import com.microservice.playground.order.application.dto.CreateOrdersDTO
import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.model.OrderStatus
import com.microservice.playground.order.domain.service.OrderService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@RestController
class V1ApiController(private val orderService: OrderService) {
    @PostMapping(path = ["/v1/{customerId}/order"])
    fun createOrder(@PathVariable customerId: String,
                    @RequestBody createOrdersDTO: CreateOrdersDTO) {
        val orderedAt = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val order = Order(
            orderId = UUID.randomUUID().toString(),
            customerId = customerId,
            orderDetails = createOrdersDTO.orderDetails,
            orderedDate = orderedAt,
            status = OrderStatus.ORDER_REQUEST,
        )
        orderService.createOrder(order)
    }
}