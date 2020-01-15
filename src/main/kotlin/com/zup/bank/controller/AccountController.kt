package com.zup.bank.controller

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.model.Account
import com.zup.bank.service.ServiceAcc
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/account")
class AccountController(val accountService: ServiceAcc) {

    @PostMapping
    fun createAcc(@Valid @RequestBody acc: AccountDTO): ResponseEntity<Account>{

        return ResponseEntity(accountService.createAcc(acc), HttpStatus.CREATED)
    }

    @GetMapping
    fun getByCpfOrNumberAcc(
            @RequestParam(required = false,defaultValue = "") cpf:String ,
            @RequestParam(required = false,defaultValue = "") numberAcc: String
    ): Account {
        return accountService.getByCpfOrNumberAcc(cpf,numberAcc)
    }


    @GetMapping("/balance/{numAcc}")
    fun getByNumberAcc (@PathVariable numAcc: String):Account{
        return accountService.balance(numAcc)
    }

    @GetMapping("/getAll")
    fun getAllAcc(): MutableList<Account>{

        return accountService.getAllAcc()
    }

    @PutMapping("/disableAcc/{cpf}")
    fun disabledAcc(@PathVariable cpf: String) : ResponseEntity<Account>{

        return ResponseEntity(accountService.disableAcc(cpf),HttpStatus.OK)

    }

    @PutMapping("/deposit")
    fun deposit(@Valid @RequestBody depositDTO: DepositDTO ): ResponseEntity<Account>{

        return ResponseEntity(accountService.deposit(depositDTO),HttpStatus.ACCEPTED)
    }

    @PutMapping("/withdraw")
    fun withdraw(@Valid @RequestBody acc: DepositDTO ): ResponseEntity<Account>{

        return ResponseEntity(accountService.withdraw(acc),HttpStatus.ACCEPTED)
    }


}