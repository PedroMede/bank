package com.zup.bank.repository

import com.zup.bank.model.Account
import org.springframework.data.jpa.repository.JpaRepository


interface AccountRepository : JpaRepository<Account, Long> {

    fun findByNumAcc(numAcc: Long): Account
    fun findByHolderCpf(cpf: String) : Account
    fun existsByNumAcc(numAcc: Long): Boolean
    fun existsByHolderCpf(cpf: String) : Boolean

}