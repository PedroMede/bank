package com.zup.bank.common.kafka

import com.google.gson.Gson

import com.zup.bank.dto.TransferDTO
import com.zup.bank.exception.customErrors.NotSufficientBalanceException
import com.zup.bank.exception.customErrors.TranferToSameAccException

import com.zup.bank.service.ServiceTransfer
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.*


@Component
class ConsumerKafka (private val transferService: ServiceTransfer){

    @KafkaListener(topics = ["transfer"], groupId = "operation")
    fun receive(data: String){
        try {

            transferService.transfer(Gson().fromJson(data, TransferDTO::class.java))

        }catch (e: NotSufficientBalanceException){

        }catch (e: EmptyResultDataAccessException){

        }catch (e: TranferToSameAccException){

        }
    }

    companion object{

        private val LOGGER = LoggerFactory.getLogger(ConsumerKafka::class.java)
 }
}