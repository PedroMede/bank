package com.zup.bank.unit.serviceTest

import com.zup.bank.dto.TransferDTO
import com.zup.bank.enum.TypeOperation
import com.zup.bank.exception.customErrors.NotSufficientBalanceException
import com.zup.bank.exception.customErrors.TransferToSameAccException
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.model.Transfer
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.repository.TransferRepository
import com.zup.bank.service.serviceImpl.TransferServImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.kafka.core.KafkaTemplate
import java.util.*


class TransferTest {

    private val servTransfer = TransferServImpl(
            Mockito.mock(AccountRepository::class.java),
            Mockito.mock(OperationsRepository::class.java),
            Mockito.mock(TransferRepository::class.java),
            Mockito.mock(KafkaTemplate::class.java) as KafkaTemplate<String, String>
    )

    @Mock
    lateinit var origin: String
    lateinit var dest: String
    lateinit var accOrigin: Account
    lateinit var accDestiny: Account
    lateinit var clientOrigin: Client
    lateinit var clientDestiny: Client
    lateinit var transferDTO: TransferDTO
    lateinit var operationOrigin: Operations
    lateinit var operationDestiny: Operations
    lateinit var transfer: Transfer

    @Before
    fun create(){
        transferDTO  =  TransferDTO("18","19",20.00 )
        clientOrigin = Client(1,"Pedro","pedro@gmail.com","42511229846")
        clientDestiny = Client(2,"Lucia","lucia@gmail.com","88804879653")

        accOrigin = Account(1,"0001","18", clientOrigin,100.00,true)
        accDestiny = Account(1,"0001","19", clientDestiny,100.00,true)
        operationOrigin = Operations(1,TypeOperation.TRANFER,50.00, Date(),accOrigin)
        operationDestiny = Operations(1,TypeOperation.TRANFER,50.00, Date(),accDestiny)
        transfer = Transfer(1,accOrigin ,accDestiny,20.00)
    }


    @Test(expected = TransferToSameAccException::class)
    fun `Tranfer into same account`(){
        origin = "18"
        dest = "18"

        servTransfer.existOrEqualsAcc(origin,dest)
    }

    @Test
    fun `method transfer working sucessfuly`(){

        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("18")).thenReturn(accOrigin)
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("19")).thenReturn(accDestiny)
        Mockito.`when`(servTransfer.accRepository.save(accOrigin)).thenReturn(accOrigin)
        Mockito.`when`(servTransfer.accRepository.save(accOrigin)).thenReturn(accDestiny)
        Mockito.`when`(servTransfer.opRepository.save(operationDestiny)).thenReturn(operationDestiny)
        Mockito.`when`(servTransfer.opRepository.save(operationOrigin)).thenReturn(operationDestiny)
        Mockito.`when`(servTransfer.transferRepo.save(Mockito.any(Transfer::class.java))).thenReturn(transfer)

        servTransfer.transfer(this.transferDTO)

        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("18")
        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("19")
        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .save(accOrigin)
        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .save(accDestiny)
        Mockito.verify(servTransfer.transferRepo,Mockito.times(1))
            .save(Mockito.any(Transfer::class.java))
    }

    @Test(expected = NotSufficientBalanceException::class)
    fun `method transfer not working because balance not sufficient`(){
        val originBalanceNotSuff = Account(1,"0001","18", clientOrigin,10.00,true)
        val transferBalanceNotSuff = TransferDTO("18","19",20.00 )
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("18")).thenReturn(originBalanceNotSuff)
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("19")).thenReturn(accDestiny)

        servTransfer.transfer(transferBalanceNotSuff)

        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("18")
        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("19")
    }
}