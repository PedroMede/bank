package com.zup.bank.service.serviceImpl

import com.zup.bank.common.AllCodeErrors
import com.zup.bank.enum.ClientStatus
import com.zup.bank.exception.customErrors.ClientInProcessException
import com.zup.bank.exception.customErrors.ExceptionClientAlreadyReg
import com.zup.bank.model.Waitlist
import com.zup.bank.model.Client
import com.zup.bank.repository.WaitListRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.RuntimeService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ClientServImp(

    val clientRepository: ClientRepository,
    val blackBlockedRepositoryRepo: WaitListRepository,
    val runtimeService: RuntimeService
) : ServiceClient {

    override fun createClient(client: Client) {
        client.status = ClientStatus.CREATED
        clientRepository.save(client)
    }

    override fun startCamunda(client: Client): Client {
        validateClient(client)
        validateStatus(client)

        if (blackBlockedRepositoryRepo.existsByCpf(client.cpf!!)){
            val clientB = blackBlockedRepositoryRepo.findByCpf(client.cpf!!)
            clientB.status = ClientStatus.PROCESSING
            blackBlockedRepositoryRepo.save(clientB)
        }else{
            val clientProcessing = Waitlist(null, client.cpf, client.status)
            blackBlockedRepositoryRepo.save(clientProcessing)
        }


        val variables = mutableMapOf<String, Any>()
        variables["name"] = client.name!!
        variables["email"] = client.email!!
        variables["cpf"] = client.cpf!!
        variables["status"] = client.status!!
        runtimeService.startProcessInstanceByKey("RegisterClient", variables)

        return client
    }

    override fun deleteBycpf(cpf: String) {
        clientRepository.deleteByCpf(cpf)
    }

    override fun getAllClient(): MutableList<Client> {
        return clientRepository.findAll()
    }

    override fun getByCpf(cpf: String): Client {
        return clientRepository.findByCpf(cpf)
    }

    private fun validateClient(client: Client) {
        if (clientRepository.existsByCpf(client.cpf!!)) {
            throw ExceptionClientAlreadyReg(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODECLIENTREGISTERED.code, "cpf")
        }
    }

    private fun validateStatus(client: Client) {

        try {
            val clientB: Waitlist = blackBlockedRepositoryRepo.findByCpfAndStatus(client.cpf!!, client.status)
            if (clientB.status == ClientStatus.PROCESSING) {
                throw ClientInProcessException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AllCodeErrors.CODECLIENTPROCESS.code)
            }
        } catch (e: ClientInProcessException) {
            throw ClientInProcessException(
                e.statusError, e.warnings, e.timestamp)
        } catch (e: EmptyResultDataAccessException) {

        }
    }


}