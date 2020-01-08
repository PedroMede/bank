package com.zup.bank.serviceTest

import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.serviceImpl.TransferServImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
class TransferTest {

    @InjectMocks
    lateinit var servTransfer: TransferServImpl

    @Mock
    lateinit var accRepo: AccountRepository

    @Mock
    lateinit var opRepo: OperationsRepository
    lateinit var origin: String
    lateinit var dest: String

    @Before
    fun create(){

    }

    @Test(expected = Exception::class)
    fun notexistOrigin(){
        Mockito.`when`(accRepo.existsByNumberAcc("18")).thenReturn(false)

        servTransfer.existOrEqualsAcc("18","23")

    }

    @Test(expected = Exception::class)
    fun notexistDestiny(){
        Mockito.`when`(accRepo.existsByNumberAcc("18")).thenReturn(true)
        Mockito.`when`(accRepo.existsByNumberAcc("23")).thenReturn(false)

        servTransfer.existOrEqualsAcc("18","23")

    }

    @Test(expected = Exception::class)
    fun equals(){
        Mockito.`when`(accRepo.existsByNumberAcc("23")).thenReturn(true)
        Mockito.`when`(accRepo.existsByNumberAcc("23")).thenReturn(true)
        Mockito.`when`(accRepo.existsByNumberAcc("23")).thenReturn(true)


        servTransfer.existOrEqualsAcc("23","23")

    }

    @Test
    fun existOrEqualOk(){
        origin = "18"
        dest = "23"
        Mockito.`when`(accRepo.existsByNumberAcc(origin)).thenReturn(true)
        Mockito.`when`(accRepo.existsByNumberAcc(dest)).thenReturn(true)


        servTransfer.existOrEqualsAcc(origin,dest)

    }


}