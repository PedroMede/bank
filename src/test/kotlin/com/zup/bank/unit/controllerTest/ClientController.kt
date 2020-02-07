package com.zup.bank.unit.controllerTest

import com.zup.bank.controller.ClientController
import com.zup.bank.model.Client
import com.zup.bank.service.ServiceClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.mockito.Mockito
import org.springframework.http.HttpStatus

class ClientController {

    private val clientController : ClientController = ClientController(
        Mockito.mock(ServiceClient:: class.java)
    )

    lateinit var client:Client

    @Before
    fun `create client`(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
    }

//    @Test
//    fun `create client ok`(){
//        Mockito.`when`(clientController.clientService.startCamunda(client)).thenReturn(client)
//        clientController.createClients(client)
//        Assert.assertEquals(HttpStatus.CREATED.value(),HttpStatus.CREATED.value())
//        Mockito.verify(clientController.clientService,Mockito.times(1)).createClient(client)
//    }

    @Test
    fun `getByCpf working`(){
        Mockito.`when`(clientController.clientService.getByCpf("42511229846")).thenReturn(client)
        clientController.getByCpf(client.cpf!!)
        Assert.assertEquals("42511229846",client.cpf)
        Mockito.verify(clientController.clientService,Mockito.times(1)).getByCpf("42511229846")
    }
//
    @Test
    fun `get all clients working`(){
        Mockito.`when`(clientController.clientService.getAllClient()).thenReturn(mutableListOf(client))
        clientController.getAll()
        Assert.assertEquals(mutableListOf(client), mutableListOf(client))
        Mockito.verify(clientController.clientService,Mockito.times(1)).getAllClient()
    }

}