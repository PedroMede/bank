package com.zup.bank.common.camunda.task

import com.zup.bank.model.Client
import com.zup.bank.repository.BlacklistRepository
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service


@Service
class ChangeStatus (
    private val blacklistRepo: BlacklistRepository,
    private val clientService: ServiceClient
): JavaDelegate {
    override fun execute(execute: DelegateExecution) {
        val client = Client()
        client.name = execute.variables.get(key = "name") as String
        client.email = execute.variables.get(key= "email") as String
        client.cpf = execute.variables.get(key="cpf") as String

        if (blacklistRepo.existsByCpf(client.cpf!!)){

        }else{
            clientService.createClient(client)
        }

    }
}