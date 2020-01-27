package com.zup.bank.controller

import com.google.gson.Gson
import com.zup.bank.dto.TransferDTO
import com.zup.bank.dto.TransferDTOResponse
import com.zup.bank.model.Transfer
import com.zup.bank.service.ServiceTransfer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/transfer")
class TransferController(val transferServ: ServiceTransfer) {

    @PostMapping
    fun postKafka (@Valid @RequestBody transferDto: TransferDTO): ResponseEntity<TransferDTOResponse>{

        return ResponseEntity(transferServ.postInKafka(transferDto),HttpStatus.ACCEPTED)
    }

    @GetMapping
    fun getById( @RequestParam(required = false,defaultValue = "") id:Long ) : ResponseEntity<Transfer> {

        return ResponseEntity(transferServ.getById(id),HttpStatus.OK)
    }


}