package com.zup.bank.serviceImpl

import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.ServiceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientServImp : ServiceClient  {

    @Autowired
    private lateinit var  clientRepository: ClientRepository

    override fun createClient(client : Client) : Client {
        validateClient(client)
        clientRepository.save(client)
        return client
    }

    override fun getById(id:Long) : Optional<Client> {
        return clientRepository.findById(id)
    }

    override fun getAllClient(): List<Client> {
       return clientRepository.findAll()
    }

    fun validateClient(client: Client) {
        if(clientRepository.existsByCpf(client.cpf!!)){

        }
    }
}