package com.microservice.playground.order.domain.repository

import com.microservice.playground.order.domain.model.Item
import org.springframework.data.cassandra.repository.CassandraRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : CassandraRepository<Item, String> {
}