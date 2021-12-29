package com.microservice.playground.order.infrastructure

import com.google.cloud.spring.pubsub.core.PubSubTemplate
import com.google.cloud.spring.pubsub.integration.outbound.PubSubMessageHandler
import com.google.pubsub.v1.PubsubMessage
import com.microservice.playground.order.domain.repository.MessagePipelineAdaptor
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.stereotype.Repository


@Repository
class MessagePipeline(private val pubsubTemplate: PubSubTemplate) : MessagePipelineAdaptor {
    private val channel = PublishSubscribeChannel()
    private val adaptor = PubSubMessageHandler(pubsubTemplate, "order")

    fun publish() {
    }

    fun subscribe() {

    }
}