package com.zup.bank.repository

import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Waitlist
import org.springframework.data.jpa.repository.JpaRepository

interface WaitListRepository: JpaRepository<Waitlist,Long> {
    fun findByCpfAndStatus(cpf: String,status: ClientStatus) :  Waitlist
    fun deleteByCpf(cpf: String)
    fun existsByCpf(cpf: String) : Boolean
    fun findByCpf(cpf:String): Waitlist
}