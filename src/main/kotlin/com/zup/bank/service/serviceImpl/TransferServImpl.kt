package com.zup.bank.service.serviceImpl

import com.zup.bank.dto.TransferDTO
import com.zup.bank.enum.TypeOperation
import com.zup.bank.exception.AllCodeErrors
import com.zup.bank.exception.customErrors.NotSufficientBalanceException
import com.zup.bank.model.Account
import com.zup.bank.model.Operations
import com.zup.bank.model.Transfer
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.repository.TransferRepository
import com.zup.bank.service.ServiceTransfer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Service
class TransferServImpl (val accRepository: AccountRepository,
                        val opRepository: OperationsRepository,
                        val transferRepo: TransferRepository ): ServiceTransfer{

    override fun transfer(opTransfer: TransferDTO): Transfer {

        existOrEqualsAcc(opTransfer.originAcc!!,opTransfer.destinyAcc!!)

        val origin : Account = accRepository.findByNumberAcc(opTransfer.originAcc!!)
        val destiny: Account = accRepository.findByNumberAcc(opTransfer.destinyAcc!!)

        if(origin.balance!! < opTransfer.value!!){
            throw NotSufficientBalanceException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODEBALANCENOTSUFF)
        }

        var transfer = Transfer(null,origin,destiny,opTransfer.value)

        origin.balance = origin.balance!! - opTransfer.value!!
        destiny.balance = destiny.balance!! + opTransfer.value!!
        accRepository.save(origin)
        accRepository.save(destiny)

        val opOringin = Operations(null,TypeOperation.TRANFER,opTransfer.value!! * (-1),Date(),origin)

        opRepository.save(opOringin)

        val opDestiny = Operations(null,TypeOperation.TRANFER,opTransfer.value!!, Date(),destiny)

        opRepository.save(opDestiny)

        return transferRepo.save(transfer)
    }

    fun existOrEqualsAcc(originAcc: String, destinyAcc: String){

        if(originAcc == destinyAcc){
            throw Exception("Tranferencia cancelada, origem e destino iguais")
        }
    }

}