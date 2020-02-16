package com.zup.bank.service

import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Waitlist

interface ServiceWaitlist {
    fun createBlocked(blocked: Waitlist)
    fun getAll(): MutableList<Waitlist>
    fun getByCpf(cpf: String,status: ClientStatus) : Waitlist
    fun deleteByCpf(cpf:String)
    fun findByCpf(cpf: String): Waitlist
}