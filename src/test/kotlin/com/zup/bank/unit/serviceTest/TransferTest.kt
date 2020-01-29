package com.zup.bank.unit.serviceTest

import com.google.gson.Gson
import com.zup.bank.dto.ObjectKafka
import com.zup.bank.dto.TransferDTO
import com.zup.bank.dto.TransferDTOResponse
import com.zup.bank.enum.StatusTransfer
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
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.experimental.theories.suppliers.TestedOn
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.dao.EmptyResultDataAccessException
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
    lateinit var transferDTO: ObjectKafka
    lateinit var operationOrigin: Operations
    lateinit var operationDestiny: Operations
    lateinit var transfer: Transfer
    lateinit var transferResponse: Transfer
    lateinit var trDTO:TransferDTO



    @Before
    fun create(){
        trDTO = TransferDTO("18","19",20.0)
        transferDTO  =  ObjectKafka("18","19",20.00, 1 )
        clientOrigin = Client(1,"Pedro","pedro@gmail.com","42511229846")
        clientDestiny = Client(2,"Lucia","lucia@gmail.com","88804879653")

        accOrigin = Account(1,"0001","18", clientOrigin,100.00,true)
        accDestiny = Account(1,"0001","19", clientDestiny,100.00,true)
        operationOrigin = Operations(1,TypeOperation.TRANFER,50.00, Date(),accOrigin)
        operationDestiny = Operations(1,TypeOperation.TRANFER,50.00, Date(),accDestiny)
        transfer = Transfer(null,accOrigin ,accDestiny,20.00)
        transferResponse = Transfer(1,accOrigin ,accDestiny,20.00)


    }


    @Test(expected = TransferToSameAccException::class)
    fun `Tranfer into same account`(){
        origin = "18"
        dest = "18"

        servTransfer.existOrEqualsAcc(origin,dest)
    }




    @Test(expected = EmptyResultDataAccessException::class)
    fun `function post in kafka that not found account`(){
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("99")).thenThrow(EmptyResultDataAccessException::class.java)
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("88")).thenThrow(EmptyResultDataAccessException::class.java)

        val tranferForPost = TransferDTO("99","88",20.0)

        servTransfer.postInKafka(tranferForPost)

    }


    @Test
    fun `function post in kafka sucess`(){
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("18"))
            .thenReturn(accOrigin)
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("19"))
            .thenReturn(accDestiny)

        val transferArgumentCaptor: ArgumentCaptor<Transfer> = ArgumentCaptor.forClass(Transfer::class.java)
        Mockito.`when`(servTransfer.transferRepo.save(transferArgumentCaptor.capture())).thenReturn(transferResponse)

        servTransfer.postInKafka(trDTO)

        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("18")
        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("19")
        Mockito.verify(servTransfer.transferRepo,Mockito.times(1))
            .save(transferArgumentCaptor.capture())
        Mockito.verify(servTransfer.kafkaTemplate,Mockito.times(1))
            .send("transfer", Gson().toJson(transferDTO))

        Assert.assertThat(transferArgumentCaptor.value.id, CoreMatchers.nullValue());
    }

    @Test
    fun `get by id working`(){
        Mockito.`when`(servTransfer.transferRepo.findById(1)).thenReturn(Optional.of(transfer))

        servTransfer.getById(1)

        Mockito.verify(servTransfer.transferRepo,Mockito.times(1))
            .findById(1)
    }


    @Test
    fun `transfer without sufficient balance`(){


        val accountO = Account(1,"0001","18", clientOrigin,10.00,true)
        val accountD = Account(1,"0001","19", clientDestiny,100.00,true)

        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("18"))
            .thenReturn(accountO)
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("19"))
            .thenReturn(accountD)
        Mockito.`when`(servTransfer.transferRepo.findById(1))
            .thenReturn(Optional.of(transfer))

        Mockito.`when`(servTransfer.transferRepo.save(transfer))
            .thenReturn(transfer)


        servTransfer.transfer(transferDTO)

        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("18")
        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("19")

        Mockito.verify(servTransfer.transferRepo,Mockito.times(1))
            .findById(1)

        Mockito.verify(servTransfer.transferRepo,Mockito.times(1))
            .save(transfer)

    }


    @Test
    fun `transfer with balance and sucess`(){


        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("18"))
            .thenReturn(accOrigin)
        Mockito.`when`(servTransfer.accRepository.findByNumberAcc("19"))
            .thenReturn(accDestiny)
        Mockito.`when`(servTransfer.transferRepo.findById(1))
            .thenReturn(Optional.of(transfer))

        Mockito.`when`(servTransfer.accRepository.save(accOrigin))
            .thenReturn(accOrigin)

        Mockito.`when`(servTransfer.accRepository.save(accDestiny))
            .thenReturn(accDestiny)


        val operationArgumentCaptor: ArgumentCaptor<Operations> = ArgumentCaptor.forClass(Operations::class.java)
        Mockito.`when`(servTransfer.opRepository.save(operationArgumentCaptor.capture()))
            .thenReturn(operationOrigin)

        Mockito.`when`(servTransfer.opRepository.save(operationArgumentCaptor.capture()))
            .thenReturn(operationDestiny)

        Mockito.`when`(servTransfer.transferRepo.save(transfer))
            .thenReturn(transfer)

        servTransfer.transfer(transferDTO)

        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("18")
        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .findByNumberAcc("19")

        Mockito.verify(servTransfer.transferRepo,Mockito.times(1))
            .findById(1)

        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .save(accOrigin)

        Mockito.verify(servTransfer.accRepository,Mockito.times(1))
            .save(accDestiny)

        Mockito.verify(servTransfer.opRepository,Mockito.times(2))
            .save(operationArgumentCaptor.capture())


        Mockito.verify(servTransfer.opRepository,Mockito.times(2))
            .save(operationArgumentCaptor.capture())


        Mockito.verify(servTransfer.transferRepo,Mockito.times(1))
            .save(transfer)

    }


}