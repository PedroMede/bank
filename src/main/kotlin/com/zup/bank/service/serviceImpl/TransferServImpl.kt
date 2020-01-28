package com.zup.bank.service.serviceImpl

import com.google.gson.Gson
import com.zup.bank.dto.TransferDTO
import com.zup.bank.enum.StatusTransfer
import com.zup.bank.enum.TypeOperation
import com.zup.bank.common.AllCodeErrors
import com.zup.bank.dto.ObjectKafka
import com.zup.bank.dto.TransferDTOResponse
import com.zup.bank.exception.customErrors.NotSufficientBalanceException
import com.zup.bank.exception.customErrors.TransferToSameAccException
import com.zup.bank.model.Operations
import com.zup.bank.model.Transfer
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.repository.TransferRepository
import com.zup.bank.service.ServiceTransfer
import org.springframework.data.jpa.repository.Lock
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.LockModeType

@Service
class TransferServImpl(val accRepository: AccountRepository,
                       val opRepository: OperationsRepository,
                       val transferRepo: TransferRepository,
                       val kafkaTemplate: KafkaTemplate<String, String>
) : ServiceTransfer {


    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    override fun transfer(opTransfer: ObjectKafka) {

        var transfer = Transfer()

        try {

            val origin = accRepository.findByNumberAcc(opTransfer.originAcc!!)
            val destiny = accRepository.findByNumberAcc(opTransfer.destinyAcc!!)

            transfer = transferRepo.findById(opTransfer.id!!).get()

            if (origin.balance!! < opTransfer.value!!) {

                transfer.status = StatusTransfer.INTERRUPTED

                throw NotSufficientBalanceException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AllCodeErrors.CODEBALANCENOTSUFF.code)
            }

            origin.balance = origin.balance!! - opTransfer.value!!
            destiny.balance = destiny.balance!! + opTransfer.value!!
            accRepository.save(origin)
            accRepository.save(destiny)

            val opOringin = Operations(null, TypeOperation.TRANFER, opTransfer.value!! * (-1), Date(), origin)
            opRepository.save(opOringin)

            val opDestiny = Operations(null, TypeOperation.TRANFER, opTransfer.value!!, Date(), destiny)
            opRepository.save(opDestiny)

            transfer.status = StatusTransfer.AUTHORIZED
            transferRepo.save(transfer)

        } catch (e: NotSufficientBalanceException) {
            transferRepo.save(transfer)
        }

    }

    override fun postInKafka(opTransfer: TransferDTO): TransferDTOResponse {

        existOrEqualsAcc(opTransfer.originAcc!!,opTransfer.destinyAcc!!)

        val origin = accRepository.findByNumberAcc(opTransfer.originAcc!!)
        val destiny = accRepository.findByNumberAcc(opTransfer.destinyAcc!!)

        val transfer = Transfer(null, origin, destiny, opTransfer.value)
        transferRepo.save(transfer)

        val objectKafka = ObjectKafka(opTransfer.originAcc,opTransfer.destinyAcc,opTransfer.value,transfer.id)

        kafkaTemplate.send("transfer", Gson().toJson(objectKafka))

        return TransferDTOResponse(opTransfer.originAcc, opTransfer.destinyAcc, opTransfer.value)
    }

     override fun getById(id: Long) : Transfer {
        return transferRepo.findById(id).get()
    }

    fun existOrEqualsAcc(originAcc: String, destinyAcc: String) {
        if (originAcc == destinyAcc) {
            throw TransferToSameAccException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODETRANFERSAMEACC.code,AllCodeErrors.CODENUMACC.code)
        }
    }


}