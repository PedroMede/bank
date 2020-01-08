package com.zup.bank.service.serviceImpl

import com.zup.bank.dto.TransferDTO
import com.zup.bank.model.Account
import com.zup.bank.model.Operations
import com.zup.bank.model.Transfer
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.repository.TransferRepository
import com.zup.bank.service.ServiceTransfer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Service
class TransferServImpl (val accRepository: AccountRepository,
                        val opRepository: OperationsRepository,
                        var transferRepo: TransferRepository ): ServiceTransfer{

    override fun transfer(opTransfer: TransferDTO): Transfer {

        existOrEqualsAcc(opTransfer.originAcc!!,opTransfer.destinyAcc!!)

        val origin : Account = accRepository.findByNumberAcc(opTransfer.originAcc!!)
        val destiny: Account = accRepository.findByNumberAcc(opTransfer.destinyAcc!!)

        if(origin.balance!! < opTransfer.value!!){
            throw Exception("Saldo insulficiente para transferência")
        }

        var transfer = Transfer(null,origin,destiny,opTransfer.value)

        origin.balance = origin.balance!! - opTransfer.value!!
        destiny.balance = destiny.balance!! + opTransfer.value!!
        accRepository.save(origin)
        accRepository.save(destiny)

        val opOringin = Operations(null,"TRANSFER",opTransfer.value!! * (-1),Date(),origin)

        opRepository.save(opOringin)

        val opDestiny = Operations(null,"TRANSFER",opTransfer.value!!, Date(),destiny)

        opRepository.save(opDestiny)

        return transferRepo.save(transfer)
    }

    fun existOrEqualsAcc(originAcc: String, destinyAcc: String){

       if(!accRepository.existsByNumberAcc(originAcc)) {
           throw Exception("Conta de origem inválida")
       }

        if(!accRepository.existsByNumberAcc(destinyAcc)) {
            throw Exception("Conta de destino inválida")
        }

        if(originAcc == destinyAcc){
            throw Exception("Tranferencia cancelada, origem e destino iguais")
        }
    }

}