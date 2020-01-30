package com.zup.bank.service.serviceImpl

import com.zup.bank.common.AllCodeErrors
import com.zup.bank.exception.customErrors.ExceptionClientAlreadyReg
import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.ServiceClient
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ClientServImp (

        val  clientRepository: ClientRepository

): ServiceClient {

    override fun createClient(client : Client) : Client {
        validateClient(client)
        clientRepository.save(client)
        return client
    }

    override fun getAllClient(): MutableList<Client> {
       return clientRepository.findAll()
    }

    override fun getByCpf(cpf: String): Client {
        return clientRepository.findByCpf(cpf)
    }

    fun validateClient(client: Client) {
        if(clientRepository.existsByCpf(client.cpf!!)){
            throw ExceptionClientAlreadyReg(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODECLIENTREGISTERED.code, "cpf")
        }
    }


}