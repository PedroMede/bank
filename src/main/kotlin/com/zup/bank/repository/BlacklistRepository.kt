package com.zup.bank.repository

import com.zup.bank.model.Blacklist
import org.springframework.data.jpa.repository.JpaRepository


interface BlacklistRepository :  JpaRepository<Blacklist,Long> {
    fun existsByCpf(cpf: String) : Boolean
}