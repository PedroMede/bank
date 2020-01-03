package com.zup.bank.serviceImpl

import com.zup.bank.model.Operations
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.ServiceOperations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OperationServImpl : ServiceOperations  {

    @Autowired
    private lateinit var operationRepository: OperationsRepository

    override fun bankStatement(): MutableList<Operations> {
        return operationRepository.findAll()
    }


}