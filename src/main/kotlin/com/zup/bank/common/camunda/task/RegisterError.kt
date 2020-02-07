package com.zup.bank.common.camunda.task

import com.zup.bank.enum.ClientStatus
import com.zup.bank.service.ServiceBlackBlocked
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service
class RegisterError (
    val serviceBlackBlocked: ServiceBlackBlocked

) : JavaDelegate{

    override fun execute(execution: DelegateExecution) {
        val cpf = execution.variables.get(key="cpf") as String
        val clientBlock = serviceBlackBlocked.getByCpf(cpf,ClientStatus.PROCESSING)
        clientBlock.status = ClientStatus.BLOCKED
        serviceBlackBlocked.createBlocked(clientBlock)

    }
}