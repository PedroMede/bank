package com.zup.bank.service.serviceImpl

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.exception.AllCodeErrors
import com.zup.bank.exception.Messages
import com.zup.bank.exception.customErrors.ExceptionClientHasAccount
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
        var operationRepository: OperationsRepository
//        val message: Messages
) : ServiceAcc {

    override fun createAcc(account: AccountDTO): Account {

        lateinit var acc: Account


        validateAccount(account.cpf!!)


        acc = reactivateAcc(account.cpf!!)

        if (acc.numAcc != null){

            return acc

        } else {

            var client: Client = clientRepository.findByCpf(account.cpf!!)
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

        val op = Operations(null,"DEPOSIT",accDTO.value, Date(),acc)

        operationRepository.save(op)

        return accRepository.save(acc)
    }

    override fun withdraw(accDTO: DepositDTO) : Account{
        validateFields(accDTO.numberAcc!!, accDTO.cpf!!)
        val acc: Account = accRepository.findByHolderCpf(accDTO.cpf!!)

        if(acc.balance!! < accDTO.value!!){
            throw Exception("Saldo insuficiente, operação cancelada")
        }

        acc.balance = acc.balance!! - accDTO.value!!

        val op = Operations(null,"WITHDRAW",accDTO.value!! * (-1), Date(), acc)
        operationRepository.save(op)


        return accRepository.save(acc)
    }

    override fun balance(numberAcc: String) : Account {

        if (accRepository.findByNumberAcc(numberAcc) == null){
            throw Exception("Conta inexistente")
        }

        return accRepository.findByNumberAcc(numberAcc)

    }

    override fun getByCpfOrNumberAcc(cpf: String, numberAcc: String): Account {

        val account = accRepository.findByHolderCpfOrNumberAcc(cpf,numberAcc)

        if(account == null){

        }

        return account!!
    }


    fun validateAccount(cpf: String){

        if(accRepository.existsByHolderCpf(cpf)) {
            val acc: Account = accRepository.findByHolderCpf(cpf)
            if ( acc.active == true) {
                throw ExceptionClientHasAccount(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    AllCodeErrors.CODEACCOUNTREGISTERED, "cpf")
            }
        }

        if (!clientRepository.existsByCpf(cpf)) {
            throw Exception("Cliente não cadastrado no sistema")
        }
    }

    fun doNotExist(cpf: String){
        if (!clientRepository.existsByCpf(cpf)){
            throw Exception("Cliente não cadastrado no sistema")
        }
    }

    fun doNotExistAcc(numberAcc : String){
        if (!accRepository.existsByNumberAcc(numberAcc)){
            throw Exception("Conta inválida")
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
        doNotExist(cpf)
        doNotExistAcc(numberAcc)
        var acc :  Account = accRepository.findByHolderCpf(cpf)
        if (!(acc.numberAcc == numberAcc && acc.holder?.cpf == cpf)){
            throw Exception("Conta e cliente divergentes")
        }
    }
}