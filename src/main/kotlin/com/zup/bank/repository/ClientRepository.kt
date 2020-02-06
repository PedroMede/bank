package com.zup.bank.repository

import com.zup.bank.model.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<Client,Long> {
    fun findByCpf(cpf: String) : Client
    fun existsByCpf(cpf: String) : Boolean
    fun deleteByCpf(cpf:String)
}