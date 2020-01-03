package com.zup.bank.service

import com.zup.bank.model.Operations
import org.springframework.stereotype.Service


interface ServiceOperations {

    fun bankStatement(): MutableList<Operations>
}