package com.zup.bank.service

import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.BlockedClient

interface ServiceBlackBlocked {
    fun createBlocked(blocked: BlockedClient)
    fun getAll(): MutableList<BlockedClient>
    fun getByCpf(cpf: String,status: ClientStatus) : BlockedClient
    fun deleteByCpf(cpf:String)
}