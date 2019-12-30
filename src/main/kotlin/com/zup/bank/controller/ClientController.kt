package com.zup.bank.controller

import com.zup.bank.model.Client
import com.zup.bank.service.ServiceClient
import org.hibernate.ObjectNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/client")
class ClientController(val clientService: ServiceClient ) {

    @PostMapping
    fun createClient(@RequestBody @Valid client: Client , bindingResult: BindingResult): ResponseEntity<Client>{
        if(bindingResult.hasErrors()){
            ResponseEntity.badRequest().body(bindingResult.getFieldError())
        }
        clientService.createClient(client)
        return ResponseEntity.ok().body(client)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id :Long) : Optional<Client>{
        return clientService.getById(id)
    }

    @GetMapping("/cpf/{cpf}")
    fun getByCpf(@PathVariable cpf: String) : Client{
        return clientService.getByCpf(cpf)
    }

    @GetMapping
    fun getAll() : MutableList<Client>{
        return clientService.getAllClient();
    }
}