package com.microservice.playground.order.infrastructure.repository

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.eventbridge.EventBridgeAsyncClient
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry


@Repository
class EventPublisher(@Value("\${aws.eventBridge.eventBus}") private val eventBus: String) {
    private val eventBridgeAsyncClient = EventBridgeAsyncClient.builder().build()
    private val jsonMapper = jacksonObjectMapper()

    fun <EventDetail> publish(eventDetailType: String, eventDetail: EventDetail) {
        val requestEntry = PutEventsRequestEntry.builder()
            .source("custom.myATMapp")
            .eventBusName(eventBus)
            .detailType(eventDetailType)
            .detail(jsonMapper.writeValueAsString(eventDetail))
            .build()

        val requestEntries: MutableList<PutEventsRequestEntry> = mutableListOf(requestEntry)
        val eventsRequest = PutEventsRequest.builder()
            .entries(requestEntries)
            .build()
        eventBridgeAsyncClient.putEvents(eventsRequest)
    }
}
