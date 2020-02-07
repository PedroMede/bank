package com.zup.bank.unit.serviceTest


import com.zup.bank.model.BlockedClient
import com.zup.bank.repository.BlacklistBlockedRepository
import com.zup.bank.service.serviceImpl.BlackBlockedImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import org.mockito.Mockito


class BlockedListTest {

    private val blockedService: BlackBlockedImpl= BlackBlockedImpl(
        Mockito.mock(BlacklistBlockedRepository::class.java))

    @Mock
    lateinit var clientB: BlockedClient

    @Before
    fun create(){
    clientB = BlockedClient(1,"42511229846")

    }

    @Test
    fun `test createBlocked`(){
        Mockito.`when`(blockedService.service.save(clientB)).thenReturn(clientB)

        blockedService.createBlocked(clientB)

        Mockito.verify(blockedService.service,Mockito.times(1)).save(clientB)
    }

    @Test
    fun `test get all blocked list `(){
        Mockito.`when`(blockedService.service.findAll()).thenReturn(mutableListOf(clientB))

        blockedService.getAll()

        Mockito.verify(blockedService.service,Mockito.times(1)).findAll()
    }

    @Test
    fun`test function get by cpf and status`(){
        Mockito.`when`(blockedService.service.findByCpfAndStatus("42511229846",clientB.status)).thenReturn(clientB)

        blockedService.getByCpf("42511229846", clientB.status)

        Mockito.verify(blockedService.service,Mockito.times(1)).findByCpfAndStatus("42511229846",clientB.status)
    }


    @Test
    fun `function delete by cpf`(){
        Mockito.doNothing().`when`(blockedService.service).deleteByCpf("42511229846")

        blockedService.deleteByCpf("42511229846")

        Mockito.verify(blockedService.service,Mockito.times(1)).deleteByCpf("42511229846")
    }





}