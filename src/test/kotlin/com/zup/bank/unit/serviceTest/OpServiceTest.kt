package com.zup.bank.unit.serviceTest

import com.zup.bank.enum.TypeOperation
import com.zup.bank.exception.customErrors.AccountNotFoundException
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.serviceImpl.OperationServImpl
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.*

class OpServiceTest {

    private val operationServ = OperationServImpl(

            Mockito.mock(OperationsRepository::class.java)

    )

    lateinit var acc: Account
    lateinit var op: Operations
    lateinit var client: Client

    @Before
    fun `create variables that are used in Operation Service`(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        acc = Account(1,"0001","18", client,100.00,true)
        op = Operations(1,TypeOperation.DEPOSIT,50.00,Date(),acc)
    }


    @Test
    fun `bank Statement of all accounts`(){

        Mockito.`when`(operationServ.operationRepository.findAll()).thenReturn(mutableListOf(op))

        operationServ.bankStatement()
        Assert.assertThat(op, CoreMatchers.notNullValue())

        Mockito.verify(operationServ.operationRepository, Mockito.times(1)).findAll()
    }


    @Test(expected = AccountNotFoundException::class)
    fun `não existe número de conta`(){
        Mockito.`when`(operationServ.operationRepository.existsByAccountNumberAcc("123")).thenReturn(false)

        operationServ.validateNumberAcc("123")

        Mockito.verify(operationServ.operationRepository, Mockito.times(1))
            .existsByAccountNumberAcc("123")
    }

    @Test
    fun existAccNum(){
        Mockito.`when`(operationServ.operationRepository.existsByAccountNumberAcc("123")).thenReturn(true)

        operationServ.validateNumberAcc("123")

        Mockito.verify(operationServ.operationRepository, Mockito.times(1)).existsByAccountNumberAcc("123")
    }

    @Test
    fun `get operations bank by number account ok`(){

        Mockito.`when`(operationServ.operationRepository.existsByAccountNumberAcc(acc.numberAcc!!)).thenReturn(true)
        Mockito.`when`(operationServ.operationRepository.getAllByAccountNumberAccOrderByDateDesc("18")).thenReturn(mutableListOf(op))


        val listOperation: MutableList<Operations>  =  operationServ.getAllBankStByNumberAcc("18")
        Assert.assertThat(listOperation, CoreMatchers.notNullValue())

        Mockito.verify(operationServ.operationRepository,Mockito.times(1)).existsByAccountNumberAcc("18")
        Mockito.verify(operationServ.operationRepository, Mockito.times(1)).getAllByAccountNumberAccOrderByDateDesc("18")
    }




}