package com.zup.bank.serviceImpl

import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.ServiceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientServImp : ServiceClient {

    @Autowired
    private lateinit var  clientRepository: ClientRepository

    override fun createClient(client : Client) : Client {
        validateClient(client)
        clientRepository.save(client)
        return client
    }

    override fun getById(id:Long) : Optional<Client> {
        if (!clientRepository.existsById(id)){
            throw Exception("Cliente não cadastrado");
        }
        return clientRepository.findById(id)
    }


    override fun getAllClient(): MutableList<Client> {
       return clientRepository.findAll()
    }

    override fun getByCpf(cpf: String): Client {
        if(!clientRepository.existsByCpf(cpf)){
            throw Exception("Cliente não cadastrado")
        }
        return clientRepository.findByCpf(cpf)
    }

//    override fun getByEmail(email: String): Client {
//         if (!clientRepository.existsByEmail(email)){
//            throw Exception("Email já existente")
//        }
//        return clientRepository.findByEmail(email)
//    }

    fun validateClient(client: Client)  {
        if(clientRepository.existsByCpf(client.cpf!!)){
            throw Exception("Cliente já existe")
        }
    }


}