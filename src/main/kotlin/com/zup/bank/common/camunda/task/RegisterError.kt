package com.zup.bank.common.camunda.task

import com.zup.bank.enum.ClientStatus
import com.zup.bank.service.ServiceWaitlist
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service
class RegisterError (
    val serviceWaitlist: ServiceWaitlist

) : JavaDelegate{

    override fun execute(execution: DelegateExecution) {
        val cpf = execution.variables.get(key="cpf") as String
        val clientBlock = serviceWaitlist.getByCpf(cpf,ClientStatus.PROCESSING)
        clientBlock.status = ClientStatus.BLOCKED
        serviceWaitlist.createBlocked(clientBlock)

    }
}