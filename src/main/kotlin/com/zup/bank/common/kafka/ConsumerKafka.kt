package com.zup.bank.common.kafka

import com.google.gson.Gson

import com.zup.bank.dto.TransferDTO

import com.zup.bank.service.ServiceTransfer
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class ConsumerKafka(private val transferService: ServiceTransfer) {

    @KafkaListener(topics = ["transfer"], groupId = "operation")
    fun receive(data: String) {

        transferService.transfer(Gson().fromJson(data, TransferDTO::class.java))

    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(ConsumerKafka::class.java)
    }

}