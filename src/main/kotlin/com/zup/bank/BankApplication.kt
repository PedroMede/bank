package com.zup.bank

import org.camunda.bpm.engine.RuntimeService
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class BankApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<BankApplication>(*args)
        }
    }

}