package com.zup.bank.controller

import com.zup.bank.model.Operations
import com.zup.bank.service.ServiceOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/bankStatement")
class OperationsController (val opService: ServiceOperations) {

    @GetMapping
    fun bankStatement(): MutableList<Operations>{
        return opService.bankStatement()
    }

}