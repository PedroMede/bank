package com.zup.bank.repository

import com.zup.bank.model.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface AccountRepository : JpaRepository<Account, Long> {

    fun findByHolderCpf(cpf: String) : Account
    fun findByNumberAcc(numberAcc: String) : Account
    fun existsByNumberAcc(numAcc: String): Boolean
    fun existsByHolderCpf(cpf: String) : Boolean

    @Query("SELECT acc FROM Account acc " +
            "WHERE acc.active=true AND (acc.holder.cpf = :cpf OR acc.numberAcc = :numberAcc)")
    fun findByHolderCpfOrNumberAcc(@Param(value = "cpf") cpf: String, @Param(value = "numberAcc") numberAcc:String): Account
}