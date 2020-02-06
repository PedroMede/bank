package com.zup.bank.service


import com.zup.bank.model.Blacklist

interface ServiceBlacklist {

    fun createBlack(blacklist: Blacklist) : Blacklist
    fun getAll(): MutableList<Blacklist>
    fun deleteByCpf(cpf: String)
    fun deleteAll()

}