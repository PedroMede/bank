package com.zup.bank.unit.serviceTest


import com.zup.bank.model.Waitlist
import com.zup.bank.repository.WaitListRepository
import com.zup.bank.service.serviceImpl.WaitlistImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import org.mockito.Mockito
import org.springframework.dao.EmptyResultDataAccessException


class WaitListTest {

    private val waitlistService: WaitlistImpl= WaitlistImpl(
        Mockito.mock(WaitListRepository::class.java))

    @Mock
    lateinit var clientB: Waitlist

    @Before
    fun create(){
    clientB = Waitlist(1,"42511229846")

    }

    @Test
    fun `test createBlocked`(){
        Mockito.`when`(waitlistService.service.save(clientB)).thenReturn(clientB)

        waitlistService.createBlocked(clientB)

        Mockito.verify(waitlistService.service,Mockito.times(1)).save(clientB)
    }

    @Test
    fun `test get all blocked list `(){
        Mockito.`when`(waitlistService.service.findAll()).thenReturn(mutableListOf(clientB))

        waitlistService.getAll()

        Mockito.verify(waitlistService.service,Mockito.times(1)).findAll()
    }

    @Test
    fun`test function get by cpf and status`(){
        Mockito.`when`(waitlistService.service.findByCpfAndStatus("42511229846",clientB.status)).thenReturn(clientB)

        waitlistService.getByCpf("42511229846", clientB.status)

        Mockito.verify(waitlistService.service,Mockito.times(1)).findByCpfAndStatus("42511229846",clientB.status)
    }


    @Test
    fun `function delete by cpf`(){
        Mockito.doNothing().`when`(waitlistService.service).deleteByCpf("42511229846")

        waitlistService.deleteByCpf("42511229846")

        Mockito.verify(waitlistService.service,Mockito.times(1)).deleteByCpf("42511229846")
    }

    @Test
    fun `get by cpf working successfully`(){
        Mockito.`when`(waitlistService.service.findByCpf("42511229846")).thenReturn(clientB)
        waitlistService.findByCpf("42511229846")
        Mockito.verify(waitlistService.service,Mockito.times(1)).findByCpf("42511229846")
    }

    @Test(expected = EmptyResultDataAccessException::class)
    fun `find by cpf not working`(){
        Mockito.`when`(waitlistService.service.findByCpf("12345678946")).thenThrow(EmptyResultDataAccessException::class.java)
        waitlistService.findByCpf("12345678946")
        Mockito.verify(waitlistService.service,Mockito.times(1)).findByCpf("12345678946")
    }


}