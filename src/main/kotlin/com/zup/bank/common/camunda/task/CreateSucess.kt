package com.zup.bank.common.camunda.task

import com.zup.bank.model.Client
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service
class CreateSucess (private val serviceClient: ServiceClient) : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val cpf :String = execution.variables.get(key="cpf") as String
        val client = serviceClient.getByCpf(cpf)

        serviceClient.createClient(client)
    }
}