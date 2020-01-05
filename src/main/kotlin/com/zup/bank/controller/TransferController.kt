package com.zup.bank.controller

import com.zup.bank.dto.TransferDTO
import com.zup.bank.model.Transfer
import com.zup.bank.service.ServiceTransfer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/transfer")
class TransferController(val transferServ: ServiceTransfer) {

    @PostMapping
    fun transfer (@Valid @RequestBody transferDto: TransferDTO, bindResult: BindingResult): ResponseEntity<Transfer>{
        if (bindResult.hasErrors()){
            badRequest().body(bindResult.allErrors)
        }

        return ResponseEntity(transferServ.transfer(transferDto),HttpStatus.CREATED)
    }
}