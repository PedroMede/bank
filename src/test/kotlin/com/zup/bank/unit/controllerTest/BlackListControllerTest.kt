package com.zup.bank.unit.controllerTest


import com.zup.bank.controller.BlacklistController
import com.zup.bank.model.Blacklist

import com.zup.bank.service.ServiceBlacklist
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class BlackListControllerTest {

    private val blackController : BlacklistController = BlacklistController(
        Mockito.mock(ServiceBlacklist:: class.java)
    )

    @Mock
    lateinit var clientB : Blacklist
    @Before
    fun create(){
        clientB = Blacklist(1,"42511229846")
    }

    @Test
    fun `create black list`(){
        Mockito.`when`(blackController.blackService.createBlack(clientB)).thenReturn(clientB)

        blackController.createBlockClient(clientB)

        Mockito.verify(blackController.blackService,Mockito.times(1)).createBlack(clientB)
    }


    @Test
    fun `get all accounts`(){
        Mockito.`when`(blackController.blackService.getAll()).thenReturn(mutableListOf(clientB))
        blackController.getAll()
        Assert.assertEquals(mutableListOf(clientB), mutableListOf(clientB))
        Mockito.verify(blackController.blackService,Mockito.times(1)).getAll()
    }

    @Test
    fun `delete by cpf`(){
        Mockito.doNothing().`when`(blackController.blackService).deleteByCpf(clientB.cpf!!)

        blackController.delete(clientB.cpf!!)

        Mockito.verify(blackController.blackService,Mockito.times(1)).deleteByCpf(clientB.cpf!!)
    }

}