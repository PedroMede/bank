package com.zup.bank.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zup.bank.dto.TransferDTO
import com.zup.bank.model.Transfer
import com.zup.bank.service.ServiceTransfer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/transfer")
class TransferController(val transferServ: ServiceTransfer, val kafkaTemplate : KafkaTemplate<String, String>) {

    @PostMapping
    fun transfer (@Valid @RequestBody transferDto: TransferDTO): ResponseEntity<Transfer>{

        return ResponseEntity(transferServ.transfer(transferDto),HttpStatus.CREATED)
    }

    @PostMapping("/kafka")
    fun postKafka(@RequestBody transferDto: TransferDTO){
        kafkaTemplate.send("transfer", ObjectMapper().writeValueAsString(transferDto))
    }

 //   @GetMapping("/kafka/{numAcc}")

}