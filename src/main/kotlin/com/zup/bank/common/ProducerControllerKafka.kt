package com.zup.bank.common

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerControllerKafka (val kafkaTemplate: KafkaTemplate<String, String>) {

    @GetMapping("/{id}")
    fun listen(@PathVariable id: String): String {

       return kafkaTemplate.send()
    }

}