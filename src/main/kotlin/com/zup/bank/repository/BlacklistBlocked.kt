package com.zup.bank.repository

import com.zup.bank.model.BlockedClient
import org.springframework.data.jpa.repository.JpaRepository

interface BlacklistBlocked: JpaRepository<BlockedClient,Long> {
    fun findByCpf(cpf: String) :  BlockedClient
    fun existsByCpf(cpf: String) : Boolean
}