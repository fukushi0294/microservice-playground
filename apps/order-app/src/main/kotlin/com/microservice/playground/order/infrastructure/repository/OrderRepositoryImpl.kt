package com.microservice.playground.order.infrastructure.repository

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.repository.OrderRepository
import com.microservice.playground.order.infrastructure.dto.OrderEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Repository
class OrderRepositoryImpl(@Value("\${aws.dynamoDB.endpoint}") dynamoDbEndPointUrl: String,
                          @Value("\${aws.dynamoDB.table}") private val dynamoDbTableName: String,) : OrderRepository {
    private val dynamoDbClient: DynamoDbEnhancedClient

    init {
        val client = DynamoDbClient.builder()
            .endpointOverride(URI.create(dynamoDbEndPointUrl))
            .build()
        this.dynamoDbClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(client)
            .build()
    }

    override fun saveOrder(order: Order) {
        val entity = OrderEntity.from(order)
        val orderTable = dynamoDbClient.table(dynamoDbTableName, TableSchema.fromBean(OrderEntity::class.java))
        orderTable.putItem(entity)
    }

    override fun getOrders(customerId: String): List<Order> {
        TODO("Not yet implemented")
    }
}