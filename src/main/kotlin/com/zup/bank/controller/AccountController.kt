package com.zup.bank.controller

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.model.Account
import com.zup.bank.service.ServiceAcc
import com.zup.bank.service.ServiceOperations
import org.apache.logging.log4j.util.PerformanceSensitive
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
    fun createAcc(@Valid @RequestBody acc: AccountDTO, bindResult: BindingResult): ResponseEntity<Account>{
        if(bindResult.hasErrors()){
            badRequest().body(bindResult.allErrors)
        }

        return ResponseEntity(accountService.createAcc(acc), HttpStatus.CREATED)
    }

    @GetMapping("/cpf/{cpf}")
    fun getByHolder (@PathVariable cpf:String): Account {
        return accountService.getByHolder(cpf)
    }

    @GetMapping("/numAcc/{numAcc}")
    fun getByNumAcc (@PathVariable numAcc: Long):Account{
        return accountService.getByNumAcc(numAcc)
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

        return ResponseEntity(accountService.disableAcc(cpf),HttpStatus.CREATED)

    }

    @PutMapping("/deposit")
    fun deposit(@Valid @RequestBody depositDTO: DepositDTO , bindResult: BindingResult): ResponseEntity<Account>{

        if(bindResult.hasErrors()){
            badRequest().body(bindResult.allErrors)
        }

        return ResponseEntity(accountService.deposit(depositDTO),HttpStatus.CREATED)
    }

    @PutMapping("/withdraw")
    fun withdraw(@Valid @RequestBody acc: DepositDTO , bindResult: BindingResult): ResponseEntity<Account>{

        if(bindResult.hasErrors()){
            badRequest().body(bindResult.allErrors)
        }

        return ResponseEntity(accountService.withdraw(acc),HttpStatus.CREATED)
    }


}