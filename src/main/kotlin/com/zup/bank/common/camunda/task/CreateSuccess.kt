package com.zup.bank.common.camunda.task

import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Client
import com.zup.bank.service.ServiceBlackBlocked
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateSuccess ( val serviceClient: ServiceClient,
                      val servBlackBlocked: ServiceBlackBlocked) : JavaDelegate {
    @Transactional
    override fun execute(execution: DelegateExecution) {
        val client = Client(
            null,
            execution.variables.get(key="name") as String,
            execution.variables.get(key="email") as String,
            execution.variables.get(key="cpf") as String
            )
        serviceClient.createClient(client)
        val clientBlackBlock = servBlackBlocked.getByCpf(client.cpf!!,ClientStatus.PROCESSING)
        clientBlackBlock.status= ClientStatus.CREATED
        servBlackBlocked.createBlocked(clientBlackBlock)
    }
}