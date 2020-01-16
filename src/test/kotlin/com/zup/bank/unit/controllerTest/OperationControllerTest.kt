package com.zup.bank.unit.controllerTest


import com.zup.bank.controller.OperationsController
import com.zup.bank.enum.TypeOperation
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations

import com.zup.bank.service.ServiceOperations
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito
import java.util.*


class OperationControllerTest {

    private val operationController : OperationsController = OperationsController(
        Mockito.mock(ServiceOperations:: class.java)
    )

    lateinit var operation: Operations
    lateinit var client: Client
    lateinit var account: Account

    @Before
    fun `create Operation`(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        account = Account(1,"0001","18", client,100.00,true)
        operation = Operations(1,TypeOperation.TRANFER,20.00,Date(),account)
    }

    @Test
    fun `get all operations`(){
        Mockito.`when`(operationController.opService.bankStatement()).thenReturn(mutableListOf(operation))
        operationController.bankStatement()
        Assert.assertEquals(mutableListOf(operation), mutableListOf(operation))
        Mockito.verify(operationController.opService,Mockito.times(1)).bankStatement()
    }

    @Test
    fun `get all by number account working`(){
        Mockito.`when`(operationController.opService.getAllBankStByNumberAcc("18")).thenReturn(mutableListOf(operation))
        operationController.getAllByNumberAcc(account.numberAcc!!)

        Assert.assertEquals(mutableListOf(operation), mutableListOf(operation))
        Mockito.verify(operationController.opService,Mockito.times(1)).getAllBankStByNumberAcc(account.numberAcc!!)
    }


}