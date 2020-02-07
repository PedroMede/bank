package com.zup.bank.repository

import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.BlockedClient
import org.springframework.data.jpa.repository.JpaRepository

interface BlacklistBlocked: JpaRepository<BlockedClient,Long> {
    fun findByCpfAndStatus(cpf: String,status: ClientStatus) :  BlockedClient
    fun deleteByCpf(cpf: String)
    fun existsByCpf(cpf: String) : Boolean
    fun findByCpf(cpf:String): BlockedClient
}