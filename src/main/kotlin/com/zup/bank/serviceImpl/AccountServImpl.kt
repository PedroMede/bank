package com.zup.bank.serviceImpl

import com.zup.bank.dto.AccountDTO
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.ServiceAcc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountServImpl : ServiceAcc {

    @Autowired
    private lateinit var accRepository : AccountRepository

    @Autowired
    private lateinit var clientRepository: ClientRepository

    override fun createAcc(accountDTO: AccountDTO): Account {

        lateinit var acc: Account

        validateAccount(accountDTO.cpf!!)


        var client : Client = clientRepository.findByCpf(accountDTO.cpf!!)
        acc = Account(null,"0001", null, client, 0.0)
        acc = accRepository.save(acc)

        acc.numberAcc = acc.numAcc.toString() + (0..10).random()
        acc = accRepository.save(acc)

        return acc
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


    fun validateAccount(cpf: String){
        if (accRepository.existsByHolderCpf(cpf)){
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
}