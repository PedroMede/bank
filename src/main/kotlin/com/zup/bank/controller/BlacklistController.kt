package com.zup.bank.controller

import com.zup.bank.model.Blacklist
import com.zup.bank.service.ServiceBlacklist
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/blacklist")
class BlacklistController(private val blackService: ServiceBlacklist) {

    @PostMapping
    fun createBlockClient(@Valid @RequestBody blacklist: Blacklist): ResponseEntity<Blacklist>{
        return ResponseEntity(blackService.createBlack(blacklist),HttpStatus.CREATED)
    }

    @GetMapping
    fun getAll():ResponseEntity<MutableList<Blacklist>>{
        return ResponseEntity(blackService.getAll(),HttpStatus.OK)
    }

}