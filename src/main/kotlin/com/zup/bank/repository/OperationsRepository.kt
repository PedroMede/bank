package com.zup.bank.repository

import com.zup.bank.model.Operations
import org.springframework.data.jpa.repository.JpaRepository

interface OperationsRepository : JpaRepository<Operations,Long> {

    fun getAllByAccountNumberAcc(numberAcc:String): MutableList<Operations>
    fun existsByAccountNumberAcc(numberAcc: String):Boolean
}