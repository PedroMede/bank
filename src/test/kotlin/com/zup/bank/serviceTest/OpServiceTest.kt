package com.zup.bank.serviceTest

import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.serviceImpl.OperationServImpl
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
class OpServiceTest {

    @InjectMocks
    lateinit var opServ: OperationServImpl

    @Mock
    lateinit var opRepo: OperationsRepository

    lateinit var acc: Account
    lateinit var op: Operations
    lateinit var client: Client

    @Before
    fun `create variables that are used in Operation Service`(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        acc = Account(1,"0001","18", client,100.00,true)
        op = Operations(1,"DEPOSIT",50.00,Date(),acc)
    }


    @Test
    fun `bank Statement of all accounts`(){

        Mockito.`when`(opRepo.findAll()).thenReturn(mutableListOf(op))

        opServ.bankStatement()
        Assert.assertThat(op, CoreMatchers.notNullValue())

        Mockito.verify(opRepo, Mockito.times(1)).findAll()
    }


    @Test(expected = Exception::class)
    fun notexistAccNum(){
        Mockito.`when`(opRepo.existsByAccountNumberAcc("123")).thenReturn(false)

        opServ.validateNumberAcc("123")


    }

    @Test
    fun existAccNum(){
        Mockito.`when`(opRepo.existsByAccountNumberAcc("123")).thenReturn(true)

        opServ.validateNumberAcc("123")

        Mockito.verify(opRepo, Mockito.times(1)).existsByAccountNumberAcc("123")
    }

    @Test
    fun `get operations bank by number account ok`(){

        Mockito.`when`(opRepo.existsByAccountNumberAcc(acc.numberAcc!!)).thenReturn(true)
        Mockito.`when`(opRepo.getAllByAccountNumberAccOrderByDateDesc("18")).thenReturn(mutableListOf(op))


        val listOperation: MutableList<Operations>  =  opServ.getAllBankStByNumberAcc("18")
        Assert.assertThat(listOperation, CoreMatchers.notNullValue())

        Mockito.verify(opRepo,Mockito.times(1)).existsByAccountNumberAcc("18")
        Mockito.verify(opRepo, Mockito.times(1)).getAllByAccountNumberAccOrderByDateDesc("18")
    }




}