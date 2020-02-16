package com.zup.bank.controller

import com.zup.bank.model.Waitlist
import com.zup.bank.service.ServiceWaitlist
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/waitlist")
@RestController
class WaitlistController(val waitlistService: ServiceWaitlist) {

    @GetMapping("/{cpf}")
    fun getClientByCpf(@PathVariable cpf: String): ResponseEntity<Waitlist>{
        return ResponseEntity(waitlistService.findByCpf(cpf),HttpStatus.OK)
    }
}