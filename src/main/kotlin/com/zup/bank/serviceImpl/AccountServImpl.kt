package com.zup.bank.serviceImpl

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.enum.TypeOperation
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.ServiceAcc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountServImpl : ServiceAcc {

    @Autowired
    private lateinit var accRepository : AccountRepository

    @Autowired
    private lateinit var clientRepository: ClientRepository

    @Autowired
    private lateinit var operationRepository: OperationsRepository

    override fun createAcc(accountDTO: AccountDTO): Account {

        lateinit var acc: Account

        validateAccount(accountDTO.cpf!!)
        acc = reactivateAcc(accountDTO.cpf!!)

        if (acc != null){

            return acc

        } else {

            var client: Client = clientRepository.findByCpf(accountDTO.cpf!!)
            acc = Account(null, "0001", null, client, 0.0)
            acc = accRepository.save(acc)

            acc.numberAcc = acc.numAcc.toString() + (0..10).random()
            acc = accRepository.save(acc)

            return acc
        }
    }

    override fun getByNumAcc(numAcc: Long): Account {
        return accRepository.findByNumAcc(numAcc)
    }

    override fun getAllAcc(): MutableList<Account> {
        return accRepository.findAll()
    }

    override fun getByHolder(cpf: String): Account {
        doNotExist(cpf)
        return accRepository.findByHolderCpf(cpf)
    }

    override fun disableAcc(cpf: String) : Account {

        var acc = getByHolder(cpf)
        acc.active = false

        return accRepository.save(acc)

    }

    override fun deposit(accountDTO: DepositDTO) : Account {

        validateFields(accountDTO.numberAcc!!, accountDTO.cpf!!)

        var acc: Account = accRepository.findByHolderCpf(accountDTO.cpf!!)

        val op = Operations(null,null,null,null)

        acc.balance = acc.balance!! + accountDTO.value!!

        //insert on table operations
        op.account = acc
        op.typeOp = "DEPOSIT"
        op.value = accountDTO.value
        operationRepository.save(op)

        return accRepository.save(acc)
    }

    override fun withdraw(account: DepositDTO) : Account{
        validateFields(account.numberAcc!!, account.cpf!!)
        var acc: Account = accRepository.findByHolderCpf(account.cpf!!)

        if(acc.balance!! < account.value!! || acc.balance!! == 0.0){
            throw Exception("Saldo insuficiente, operação cancelada")
        }

        val op = Operations(null,null,null,null)

        acc.balance = acc.balance!! - account.value!!

        op.account = acc
        op.typeOp = "WITHDRAW"
        op.value = account.value
        operationRepository.save(op)


        return accRepository.save(acc)
    }

    override fun balance(numberAcc: String) : Account {

        if (accRepository.findByNumberAcc(numberAcc) == null){
            throw Exception("Conta inexistente")
        }

        return accRepository.findByNumberAcc(numberAcc)

    }


    fun validateAccount(cpf: String){
        var acc : Account = accRepository.findByHolderCpf(cpf)
        if (accRepository.existsByHolderCpf(cpf) && acc.active == true){
            throw Exception("Titular já possui uma conta cadastrada")
        }
        if (!clientRepository.existsByCpf(cpf)){
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
        var acc: Account = accRepository.findByHolderCpf(cpf)
        acc.active = true
        acc.balance = 0.0
        accRepository.save(acc)

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