package com.microservice.playground.order.infrastructure.repository

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.microservice.playground.order.domain.exception.EventPublishFailedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.eventbridge.EventBridgeClient
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequest
import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry


@Repository
class EventPublisher(
    @Value("\${aws.eventBridge.source}") private val eventSource: String,
    @Value("\${aws.eventBridge.eventBus}") private val eventBus: String
) {
    private val eventBridgeClient = EventBridgeClient.builder().build()
    private val jsonMapper = jacksonObjectMapper()

    fun <EventDetail> publish(eventDetailType: String, eventDetail: EventDetail) {
        val requestEntry = PutEventsRequestEntry.builder()
            .source(eventSource)
            .eventBusName(eventBus)
            .detailType(eventDetailType)
            .detail(jsonMapper.writeValueAsString(eventDetail))
            .build()

        val requestEntries: MutableList<PutEventsRequestEntry> = mutableListOf(requestEntry)
        this.putEvents(requestEntries)
    }

    fun putEvents(requestEntries: List<PutEventsRequestEntry>) {
        val eventsRequest = PutEventsRequest.builder()
            .entries(requestEntries)
            .build()
        eventBridgeClient.putEvents(eventsRequest).let {
            val failedPutEventsRequests = it.entries().mapIndexed { index, entry ->
                if (!entry.errorCode().isNullOrBlank()) {
                    requestEntries[index]
                } else {
                    null
                }
            }.filterNotNull()
            if (failedPutEventsRequests.isNotEmpty())
                throw EventPublishFailedException(failedPutEventsRequests)
        }
    }
}
