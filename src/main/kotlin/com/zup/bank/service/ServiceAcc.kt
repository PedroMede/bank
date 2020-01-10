package com.zup.bank.service

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.BalanceDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.model.Account

interface ServiceAcc {
    fun createAcc(account: AccountDTO): Account
    fun getAllAcc(): MutableList<Account>
    fun disableAcc(cpf:String): Account
    fun deposit (accDTO: DepositDTO): Account
    fun withdraw (accDTO: DepositDTO): Account
    fun balance (numberAcc: String): Account
    fun getByCpfOrNumberAcc (cpf: String,numberAcc: String): Account?

}