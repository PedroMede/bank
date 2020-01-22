package com.zup.bank.service.serviceImpl

import com.zup.bank.dto.TransferDTO
import com.zup.bank.enum.StatusTransfer
import com.zup.bank.enum.TypeOperation
import com.zup.bank.common.AllCodeErrors
import com.zup.bank.exception.customErrors.NotSufficientBalanceException
import com.zup.bank.exception.customErrors.TranferToSameAccException
import com.zup.bank.model.Account
import com.zup.bank.model.Operations
import com.zup.bank.model.Transfer
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.repository.TransferRepository
import com.zup.bank.service.ServiceTransfer
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.jpa.repository.Lock
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.LockModeType

@Service
class TransferServImpl (val accRepository: AccountRepository,
                        val opRepository: OperationsRepository,
                        val transferRepo: TransferRepository
                        ): ServiceTransfer{


    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    override fun transfer(opTransfer: TransferDTO): Transfer {
        
//        try {

            existOrEqualsAcc(opTransfer.originAcc!!,opTransfer.destinyAcc!!)

           val origin : Account = accRepository.findByNumberAcc(opTransfer.originAcc!!)
            val destiny : Account = accRepository.findByNumberAcc(opTransfer.destinyAcc!!)

            val transfer = Transfer(null,origin,destiny,opTransfer.value,StatusTransfer.PROCESSING)
            transferRepo.save(transfer)

            if(origin.balance!! < opTransfer.value!!){

                val transferI = Transfer(null,origin,destiny,opTransfer.value, StatusTransfer.INTERRUPTED)
                transferRepo.save(transferI)

                throw NotSufficientBalanceException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AllCodeErrors.CODEBALANCENOTSUFF.code)
            }

            origin.balance = origin.balance!! - opTransfer.value!!
            destiny.balance = destiny.balance!! + opTransfer.value!!
            accRepository.save(origin)
            accRepository.save(destiny)

            val opOringin = Operations(null,TypeOperation.TRANFER,opTransfer.value!! * (-1),Date(),origin)
            opRepository.save(opOringin)

            val opDestiny = Operations(null,TypeOperation.TRANFER,opTransfer.value!!, Date(),destiny)
            opRepository.save(opDestiny)



            transfer.status = StatusTransfer.AUTHORIZED

            return transferRepo.save(transfer)

//        }catch (e: EmptyResultDataAccessException) {
//
//        }


//        return transferRepo.save(transfer)

    }

    override fun postInKafka(opTransfer: TransferDTO){


    }

    fun existOrEqualsAcc(originAcc: String, destinyAcc: String){
        if(originAcc == destinyAcc){
            throw TranferToSameAccException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODETRANFERSAMEACC.code)
        }
    }





}