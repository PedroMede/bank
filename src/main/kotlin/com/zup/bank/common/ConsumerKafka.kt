package com.zup.bank.common

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener

class ConsumerKafka {

    @KafkaListener(topics = ["transfer"],groupId = "operation")
    fun receive(message: String){
        LOGGER.info("Received message:'$message'")
    }

    companion object{
        private val LOGGER = LoggerFactory.getLogger(ConsumerKafka::class.java)
    }
}