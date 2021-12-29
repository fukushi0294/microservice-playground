package com.microservice.playground.order.infrastructure.repository

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository
import com.google.cloud.spring.data.datastore.repository.query.Query
import com.microservice.playground.order.infrastructure.dto.OrderEntity
import org.springframework.data.repository.query.Param

interface OrderEntityRepository : DatastoreRepository<OrderEntity, String> {
    @Query("SELECT * FROM orders WHERE customer_id = @customer_id AND status != canceled")
    fun findByCustomerId(@Param("customer_id")customerId: String) : List<OrderEntity>
}