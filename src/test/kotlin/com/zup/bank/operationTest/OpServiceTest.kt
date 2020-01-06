package com.zup.bank.operationTest

import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.serviceImpl.OperationServImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner

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
        op = Operations(1,"DEPOSIT",50.00,"22/12/20 04:40",acc)
    }




    @Test
    fun banckStateOk(){

        Mockito.`when`(opRepo.findAll()).thenReturn(mutableListOf(op))

        opServ.bankStatement()

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
    fun getAllBankOk(){
    //assert
        Mockito.`when`(opRepo.existsByAccountNumberAcc(acc.numberAcc!!)).thenReturn(true)
        Mockito.`when`(opRepo.getAllByAccountNumberAccOrderByDateDesc("18")).thenReturn(mutableListOf(op))


        opServ.getAllBankStByNumberAcc("18")


        Mockito.verify(opRepo,Mockito.times(1)).existsByAccountNumberAcc("18")
        Mockito.verify(opRepo, Mockito.times(1)).getAllByAccountNumberAccOrderByDateDesc("18")
    }




}