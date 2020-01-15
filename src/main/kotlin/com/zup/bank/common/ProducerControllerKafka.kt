package com.zup.bank.common

import com.zup.bank.model.Transfer
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProducerControllerKafka (val kafkaTemplate: KafkaTemplate<String, Transfer>) {

    @GetMapping("/{msg}")
    fun sendMessage(@RequestBody transfer: Transfer){
        kafkaTemplate.send(ProducerKafka.TOPIC,transfer)
    }

}