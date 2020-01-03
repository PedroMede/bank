package com.zup.bank.service

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.model.Account

interface ServiceAcc {
    fun createAcc(account: AccountDTO): Account
    fun getByNumAcc(numAcc: Long): Account
    fun getAllAcc(): MutableList<Account>
    fun getByHolder(cpf: String): Account
    fun disableAcc(cpf:String): Account

    fun deposit (accDTO: DepositDTO): Account

    fun withdraw (value : Double, account: Account)
    fun balance (value: Double , account: Account)

}