package com.microservice.playground.order.domain.repository

interface MessagePipelineAdaptor {
    fun <T> publish(serviceName: String, payload:T)
    fun <T> subscribe(serviceName: String): T
}