package com.zup.bank.common.camunda.task

import com.zup.bank.model.Client
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service
class CreateSucess (private val serviceClient: ServiceClient) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val client= Client(
            null,
            execution.variables.get(key="name") as String,
            execution.variables.get(key= "email") as String ,
            execution.variables.get(key="cpf") as String
        )
        serviceClient.createClient(client)
    }
}