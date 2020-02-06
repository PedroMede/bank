package com.zup.bank.common.camunda.task

import com.zup.bank.model.BlockedClient
import com.zup.bank.repository.BlacklistBlocked
import com.zup.bank.service.ServiceBlackBlocked
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service
class RegisterError (
    val serviceBlackBlocked: ServiceBlackBlocked,
    val serviceClient: ServiceClient,
    val repositoryBlockedClient :  BlacklistBlocked
) : JavaDelegate{

    override fun execute(execution: DelegateExecution) {
        val cpf = execution.variables.get(key="cpf") as String

        var clientBlock = BlockedClient()
        clientBlock.cpf = cpf

        if (repositoryBlockedClient.existsByCpf(cpf)){
            clientBlock = serviceBlackBlocked.getByCpf(cpf)
            serviceBlackBlocked.createBlocked(clientBlock)
            serviceClient.deleteBycpf(clientBlock.cpf!!)
        }else {
            serviceBlackBlocked.createBlocked(clientBlock)
            serviceClient.deleteBycpf(clientBlock.cpf!!)
        }
    }
}