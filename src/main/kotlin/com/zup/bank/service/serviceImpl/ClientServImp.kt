package com.zup.bank.service.serviceImpl

import com.zup.bank.common.AllCodeErrors
import com.zup.bank.enum.ClientStatus
import com.zup.bank.exception.customErrors.ExceptionClientAlreadyReg
import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@EnableProcessApplication
@Service
class ClientServImp (

    private val  clientRepository: ClientRepository,
    private val runtimeService: RuntimeService
    ): ServiceClient {

    override fun createClient(client : Client)  {
        client.status = ClientStatus.CREATED
        clientRepository.save(client)
    }

    override fun startCamunda(client: Client) : Client {
        validateClient(client)
        clientRepository.save(client)

        val variables = mutableMapOf<String, Any>()
        variables["name"] = client.name!!
        variables["email"] = client.email!!
        variables["cpf"] = client.cpf!!
        runtimeService.startProcessInstanceByKey("RegisterClient", variables)

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