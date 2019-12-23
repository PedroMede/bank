package com.zup.bank.controller

import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.ServiceClient
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/client")
class ClientController(val clientService: ServiceClient ) {

    @PostMapping
    fun createClient(@RequestBody client: Client): Client{
        clientService.createClient(client)
        return client
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateClient(@PathVariable id: Long) {

    }
}