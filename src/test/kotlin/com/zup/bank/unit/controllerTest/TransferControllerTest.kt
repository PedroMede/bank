package com.zup.bank.unit.controllerTest

import com.zup.bank.controller.TransferController
import com.zup.bank.dto.TransferDTO
import com.zup.bank.dto.TransferDTOResponse
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Transfer
import com.zup.bank.service.ServiceTransfer
import org.junit.Before

import org.junit.Test
import org.mockito.Mockito


class TransferControllerTest {

    private val transferController : TransferController = TransferController(
        Mockito.mock(ServiceTransfer:: class.java)
    )

    lateinit var transferDTO : TransferDTO
    lateinit var accountOrigin: Account
    lateinit var accountDestiny: Account
    lateinit var client: Client
    lateinit var transfer: Transfer
    lateinit var transferResponse : TransferDTOResponse

    @Before
    fun create(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        accountDestiny = Account(2,"0001","19", client,100.00,true)
        accountOrigin = Account(1,"0001","18", client,100.00,true)
        transferDTO = TransferDTO("18","19",20.00)
        transfer = Transfer(1,accountOrigin,accountDestiny,20.00)
        transferResponse = TransferDTOResponse("18","19",20.00)
    }

    @Test
    fun createTransfer(){

        Mockito.`when`(transferController.transferServ.postInKafka(transferDTO)).thenReturn(transferResponse)

        transferController.postKafka(transferDTO)


        Mockito.verify(transferController.transferServ,Mockito.times(1)).postInKafka(transferDTO)

    }


    @Test
    fun `function get by id`(){

        Mockito.`when`(transferController.transferServ.getById(1)).thenReturn(transfer)

        transferController.getById(1)


        Mockito.verify(transferController.transferServ,Mockito.times(1)).getById(1)
    }




}