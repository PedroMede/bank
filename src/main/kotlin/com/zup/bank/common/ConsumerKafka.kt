package com.zup.bank.common

import com.zup.bank.common.ProducerKafka.Companion.TOPIC
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload

class ConsumerKafka {

    @KafkaListener(topics = [TOPIC],groupId = "operation")
    fun receive(message: String){
        LOGGER.info("Received message:'$message'")
    }

    companion object{
        private val LOGGER = LoggerFactory.getLogger(ConsumerKafka::class.java)
    }
}