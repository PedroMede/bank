package com.zup.bank.clientTest


import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.serviceImpl.ClientServImp
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner
import java.util.*


@RunWith(SpringRunner::class)
class ClientServiceTest {

    @InjectMocks
    lateinit var clientServ:  ClientServImp

    @Mock
    lateinit var clientRepository: ClientRepository

    @Mock
    lateinit var client: Client


    @Before
    fun createClient(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")

    }

    //class exception
    @Test(expected = Exception::class)
    fun existClient(){
        Mockito.`when`(clientRepository.existsByCpf("42511229846")).thenReturn(true)

        clientServ.createClient(client)


    }

    @Test
    fun notExistCli(){
        Mockito.`when`(clientRepository.existsByCpf("42511229846")).thenReturn(false)
        Mockito.`when`(clientRepository.save(Mockito.any(Client::class.java))).thenReturn(client)

        clientServ.createClient(client)

        Assert.assertEquals(client,client)

        Mockito.verify(clientRepository,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientRepository,Mockito.times(1)).save(client)
    }

//    @Test(expected = Exception::class)
//    fun getByIdNotFound(){
//        Mockito.`when`(clientRepository.existsById(1)).thenReturn(false)
//
//        clientServ.getById(1)
//    }
//
//    @Test
//    fun getByIdFound(){
//        Mockito.`when`(clientRepository.existsById(1)).thenReturn(true)
//        Mockito.`when`(clientRepository.findById(1)).thenReturn(Optional.of(client))
//
//        clientServ.getById(1)
//
//        Mockito.verify(clientRepository,Mockito.times(1)).existsById(1)
//        Mockito.verify(clientRepository,Mockito.times(1)).findById(1)
//    }

    @Test
    fun getAllClientOk(){
        Mockito.`when`(clientRepository.findAll()).thenReturn(mutableListOf(client))

        clientServ.getAllClient()

        Mockito.verify(clientRepository,Mockito.times(1)).findAll()
    }

    @Test(expected = Exception::class)
    fun getByCpfNotFound(){
        Mockito.`when`(clientRepository.existsByCpf("42511229846")).thenReturn(false)


        clientServ.getByCpf("42511229846")


    }

    @Test
    fun getByCpfFound(){
        Mockito.`when`(clientRepository.existsByCpf("42511229846")).thenReturn(true)
        Mockito.`when`(clientRepository.findByCpf("42511229846")).thenReturn(client)

        clientServ.getByCpf("42511229846")

        Mockito.verify(clientRepository,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientRepository,Mockito.times(1)).findByCpf("42511229846")
    }




}