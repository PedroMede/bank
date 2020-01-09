package com.zup.bank.serviceTest

import com.zup.bank.dto.TransferDTO
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.model.Transfer
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.repository.TransferRepository
import com.zup.bank.service.serviceImpl.AccountServImpl
import com.zup.bank.service.serviceImpl.TransferServImpl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner
import java.util.*


class TransferTest {

    private val servTransfer = TransferServImpl(
            Mockito.mock(AccountRepository::class.java),
            Mockito.mock(OperationsRepository::class.java),
            Mockito.mock(TransferRepository::class.java)
    )

    @Mock
    lateinit var origin: String
    lateinit var dest: String
    lateinit var accOrigin: Account
    lateinit var accDestiny: Account
    lateinit var clientOrigin: Client
    lateinit var clientDestiny: Client
    lateinit var transferDTO: TransferDTO
    lateinit var operations: Operations
    lateinit var transfer: Transfer

    @Before
    fun create(){
        transferDTO  =  TransferDTO("18","19",20.00 )
        clientOrigin = Client(1,"Pedro","pedro@gmail.com","42511229846")
        accOrigin = Account(1,"0001","18", clientOrigin,100.00,true)
        operations = Operations(1,"TRANSFER",50.00, Date(),accOrigin)
        clientDestiny = Client(2,"Lucia","lucia@gmail.com","88804879653")
        accDestiny = Account(1,"0001","19", clientOrigin,100.00,true)
        transfer = Transfer(null,accOrigin,accDestiny,20.00)
    }

    @Test(expected = Exception::class)
    fun notexistOrigin(){
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc("18")).thenReturn(false)

        servTransfer.existOrEqualsAcc("18","23")

    }

    @Test(expected = Exception::class)
    fun notexistDestiny(){
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc("18")).thenReturn(true)
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc("23")).thenReturn(false)

        servTransfer.existOrEqualsAcc("18","23")

    }

    @Test(expected = Exception::class)
    fun equals(){
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc("23")).thenReturn(true)
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc("23")).thenReturn(true)
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc("23")).thenReturn(true)


        servTransfer.existOrEqualsAcc("23","23")

    }

    @Test
    fun existOrEqualOk(){
        origin = "18"
        dest = "23"
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc(origin)).thenReturn(true)
        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc(dest)).thenReturn(true)


        servTransfer.existOrEqualsAcc(origin,dest)

    }

//
//    @Test(expected = Exception::class)
//    fun `transfer sucess invalid account`(){
//        Mockito.`when`(servTransfer.accRepository.existsByNumberAcc(accOrigin.numberAcc!!)).thenReturn(false)
//        Mockito.`when`(servTransfer.accRepository.findByNumberAcc(accOrigin.numberAcc!!)).thenReturn(accOrigin)
//        Mockito.`when`(servTransfer.accRepository.save(accOrigin)).thenReturn(accOrigin)
//        Mockito.`when`(servTransfer.opRepository.save(operations)).thenReturn(operations)
//        Mockito.`when`(servTransfer.transferRepo.save(transfer)).thenReturn(transfer)
//
//        servTransfer.transfer(transferDTO)
//
//        Mockito.verify(servTransfer.accRepository,Mockito.times(2))
//                .existsByNumberAcc(accOrigin.numberAcc!!)
//        Mockito.verify(servTransfer.accRepository,Mockito.times(2))
//                .findByNumberAcc(accOrigin.numberAcc!!)
//        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
//                .save(accOrigin)
//        Mockito.verify(servTransfer.opRepository,Mockito.times(2))
//                .save(operations)
//        Mockito.verify(servTransfer.transferRepo,Mockito.times(2))
//                .save(transfer)
//    }

}