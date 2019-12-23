package com.zup.bank.service

import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.*


interface ServiceClient {

    fun createClient(client : Client) : Client
    fun getById(id:Long) : Optional<Client>
    fun getAllClient() : List<Client>
}