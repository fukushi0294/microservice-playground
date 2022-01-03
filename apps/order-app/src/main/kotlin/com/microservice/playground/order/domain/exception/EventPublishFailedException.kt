package com.microservice.playground.order.domain.exception

import software.amazon.awssdk.services.eventbridge.model.PutEventsRequestEntry

class EventPublishFailedException(val putEventsRequestEntries: List<PutEventsRequestEntry>) : RuntimeException() {
}