package com.zup.bank.controller

import com.zup.bank.dto.RequestClientDTO
import com.zup.bank.model.Client
import com.zup.bank.service.ServiceClient
import org.hibernate.ObjectNotFoundException
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
    fun createClient(@Valid @RequestBody  client: Client, bindingResult: BindingResult):ResponseEntity<Client>{
        if(bindingResult.hasErrors()){
            badRequest().body(bindingResult.allErrors)
        }

        return ResponseEntity(clientService.createClient(client),HttpStatus.CREATED)

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