package com.zup.bank.common.camunda.task

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.springframework.stereotype.Service

@Service
class CreateSucess : JavaDelegate {
    override fun execute(execution: DelegateExecution) {
    }
}