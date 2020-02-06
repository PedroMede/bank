package com.zup.bank.service

import com.zup.bank.model.Client
import java.util.*


interface ServiceClient {

    fun createClient(client : Client)
    fun getAllClient() : MutableList<Client>
    fun getByCpf(cpf:String): Client
    fun startCamunda(client: Client) : Client
    fun deleteBycpf(cpf:String)
}