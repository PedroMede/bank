package com.zup.bank.service

import com.zup.bank.model.Client
import java.util.*


interface ServiceClient {

    fun createClient(client : Client) : Client
    fun getAllClient() : MutableList<Client>
    fun getByCpf(cpf:String): Client

}