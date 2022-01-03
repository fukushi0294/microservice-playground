package com.microservice.playground.order.infrastructure.repository

import com.microservice.playground.order.domain.model.Order
import com.microservice.playground.order.domain.repository.OrderRepository
import com.microservice.playground.order.infrastructure.entity.OrderEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional.keyEqualTo
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Repository
class OrderRepositoryImpl(@Value("\${aws.dynamoDB.table}") private val dynamoDbTableName: String) : OrderRepository {
    private val dynamoDbClient: DynamoDbEnhancedClient
    private val orderTable: DynamoDbTable<OrderEntity>
    init {
        val client = DynamoDbClient.builder()
            .region(Region.AP_NORTHEAST_1)
            .build()
        this.dynamoDbClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(client)
            .build()
        this.orderTable = dynamoDbClient.table(dynamoDbTableName, TableSchema.fromBean(OrderEntity::class.java))
    }

    override fun saveOrder(order: Order) {
        val entity = OrderEntity.from(order)
        orderTable.putItem(entity)
    }

    override fun getOrders(customerId: String): List<Order> {
        return orderTable.query(keyEqualTo { k -> k.partitionValue(customerId) })
            .items()
            .map { it.toOrder() }
    }

    override fun deleteOrder(customerId: String) {
        orderTable.query(keyEqualTo { k -> k.partitionValue(customerId) })
            .items()
            .forEach {
                val key = Key.builder()
                    .partitionValue(it.customerId)
                    .sortValue(it.orderedDate)
                    .build()
                orderTable.deleteItem(key)
            }
    }
}