package com.zup.bank.service

import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ServiceClient(private val clientRepository : ClientRepository) {

    fun createClient(client : Client) : Client{
        clientRepository.save(client)
        return client
    }

    fun getById(id:Long) : Optional<Client> {
        return clientRepository.findById(id)
    }

}