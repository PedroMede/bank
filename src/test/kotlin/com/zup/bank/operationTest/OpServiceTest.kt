package com.zup.bank.operationTest

import com.zup.bank.repository.ClientRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.serviceImpl.ClientServImp
import com.zup.bank.serviceImpl.OperationServImpl
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

    @Test
    fun banckStateOk(){
        //Mockito.`when`(clientRepository.findAll()).thenReturn(list!!)

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



}