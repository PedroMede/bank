package com.zup.bank.service.serviceImpl

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.enum.TypeOperation
import com.zup.bank.exception.AllCodeErrors
import com.zup.bank.exception.Messages
import com.zup.bank.exception.customErrors.AccountAndClientDivergentException
import com.zup.bank.exception.customErrors.ExceptionClientHasAccount
import com.zup.bank.exception.customErrors.NotSufficientBalanceException
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.ServiceAcc
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountServImpl(

        val accRepository : AccountRepository,
        val clientRepository: ClientRepository,
        val operationRepository: OperationsRepository
//       val message: Messages
) : ServiceAcc {

    override fun createAcc(account: AccountDTO): Account {

        lateinit var acc: Account

        validateAccount(account.cpf!!)

        acc = reactivateAcc(account.cpf!!)

        if (acc.numAcc != null){

            return acc

        } else {

            val client: Client = clientRepository.findByCpf(account.cpf!!)
            acc = Account(null, "0001", null, client, 0.0)
            acc = accRepository.save(acc)

            acc.numberAcc = acc.numAcc.toString() + (0..10).random()
            acc = accRepository.save(acc)

            return acc
        }
    }


    override fun getAllAcc(): MutableList<Account> {
        return accRepository.findAll()
    }


    override fun disableAcc(cpf: String) : Account {

        val acc = getByCpfOrNumberAcc(cpf,"")
        acc.active = false

        return accRepository.save(acc)

    }
    override fun deposit(accDTO: DepositDTO) : Account {

        validateFields(accDTO.numberAcc!!, accDTO.cpf!!)

        val acc: Account = accRepository.findByHolderCpf(accDTO.cpf!!)

        acc.balance = acc.balance!! + accDTO.value!!

        val op = Operations(null,TypeOperation.DEPOSIT,accDTO.value, Date(),acc)

        operationRepository.save(op)

        return accRepository.save(acc)
    }

    override fun withdraw(accDTO: DepositDTO) : Account{
        validateFields(accDTO.numberAcc!!, accDTO.cpf!!)
        val acc: Account = accRepository.findByHolderCpf(accDTO.cpf!!)

        if(acc.balance!! < accDTO.value!!){
            throw NotSufficientBalanceException(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODEBALANCENOTSUFF)
        }

        acc.balance = acc.balance!! - accDTO.value!!

        val op = Operations(null,TypeOperation.WITHDRAW,accDTO.value!! * (-1), Date(), acc)
        operationRepository.save(op)

        return accRepository.save(acc)
    }

    override fun balance(numberAcc: String) : Account {

        return accRepository.findByNumberAcc(numberAcc)
    }

    override fun getByCpfOrNumberAcc(cpf: String, numberAcc: String): Account {

        return  accRepository.findByHolderCpfOrNumberAcc(cpf,numberAcc)
    }


    fun validateAccount(cpf: String){

        if (accRepository.existsByHolderCpf(cpf)) {
            val acc: Account = accRepository.findByHolderCpf(cpf)
            if (acc.active == true) {
                throw ExceptionClientHasAccount(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AllCodeErrors.CODEACCOUNTREGISTERED, "cpf")
            }
        }
    }


    fun reactivateAcc(cpf: String) : Account {
        var acc = Account(null,null,null,null,null,null)
        if (accRepository.existsByHolderCpf(cpf)) {
            acc = accRepository.findByHolderCpf(cpf)
            acc.active = true
            acc.balance = 0.0

            return accRepository.save(acc)
        }

        return acc
    }

    fun validateFields(numberAcc: String, cpf: String){
        val acc :  Account = accRepository.findByHolderCpf(cpf)
        if (!(acc.numberAcc == numberAcc && acc.holder?.cpf == cpf)){
            throw AccountAndClientDivergentException(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODEACCANDCLIENTDIVERENT,"")
        }
    }
}