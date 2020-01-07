package com.zup.bank.service.serviceImpl

import com.zup.bank.model.Operations
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.ServiceOperations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OperationServImpl(val operationRepository: OperationsRepository) : ServiceOperations  {


    override fun bankStatement(): MutableList<Operations> {
        return operationRepository.findAll()
    }

    override fun getAllBankStByNumberAcc(numberAcc: String): MutableList<Operations> {
        validateNumberAcc(numberAcc)
        return operationRepository.getAllByAccountNumberAccOrderByDateDesc(numberAcc)
    }

   fun validateNumberAcc(num: String){
        if (!operationRepository.existsByAccountNumberAcc(num)){
            throw Exception("Conta não existe")
        }
    }

}