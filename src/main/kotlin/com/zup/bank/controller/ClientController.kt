package com.zup.bank.controller

import com.zup.bank.model.Client
import com.zup.bank.service.ServiceClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/client")
class ClientController(val clientService: ServiceClient ) {

    @PostMapping
    fun createClients(@Valid @RequestBody client: Client):ResponseEntity<Client>{
        return ResponseEntity(clientService.startCamunda(client),HttpStatus.OK)
    }

    @GetMapping
    fun getByCpf(@RequestParam(required = false) cpf: String) : ResponseEntity<Client>{
        return ResponseEntity(clientService.getByCpf(cpf),HttpStatus.OK)
    }

    @GetMapping("/getAll")
    fun getAll() : ResponseEntity<MutableList<Client>>{
        return ResponseEntity(clientService.getAllClient(),HttpStatus.OK)
    }
}