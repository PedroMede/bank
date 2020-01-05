package com.zup.bank.serviceImpl

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
class TransferServImpl : ServiceTransfer{

    @Autowired
    lateinit var accRepository: AccountRepository

    @Autowired
    lateinit var opRepository: OperationsRepository

    @Autowired
    lateinit var transferRepo: TransferRepository

    override fun transfer(opTransfer: TransferDTO): Transfer {

        val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yy HH:mm")
        val date = Date()

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


        val opOrin = Operations(null,null,null,null)
        opOrin.account = origin
        opOrin.typeOp = "TRANSFER"
        opOrin.value = opTransfer.value!! * (-1)
        opOrin.date = dateFormat.format(date)
        opRepository.save(opOrin)

        val opDest = Operations(null,null,null,null)
        opDest.account = destiny
        opDest.typeOp = "TRANSFER"
        opDest.value = opTransfer.value!!
        opDest.date = dateFormat.format(date)
        opRepository.save(opDest)


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