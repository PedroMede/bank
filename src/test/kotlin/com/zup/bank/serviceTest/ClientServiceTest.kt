package com.zup.bank.serviceTest


import com.zup.bank.exception.customErrors.ExceptionClientHasAccount
import com.zup.bank.model.Client
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.serviceImpl.ClientServImp
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.web.bind.MethodArgumentNotValidException


class ClientServiceTest {

    private val clientServ: ClientServImp = ClientServImp(

            Mockito.mock(ClientRepository::class.java)

    )

    @Mock
    lateinit var client: Client


    @Before
    fun createClient(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")

    }

    //class exception
    @Test(expected = ExceptionClientHasAccount::class)
    fun `test if exist client to create a new client`(){
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(true)

        clientServ.createClient(client)


    }

    @Test
    fun `not exist client so create a new client`(){
        Mockito.`when`(clientServ.clientRepository.save(client)).thenReturn(client)

        var response : Client = clientServ.createClient(client)

        Assert.assertEquals(response, client)
        Assert.assertThat(response, CoreMatchers.notNullValue())
        Assert.assertThat(response.id, CoreMatchers.`is`(1L))


        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).save(client)
    }

    @Test
    fun getAllClientOk(){
        Mockito.`when`(clientServ.clientRepository.findAll()).thenReturn(mutableListOf(client))

        clientServ.getAllClient()

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).findAll()
    }

    @Test(expected = ExceptionClientHasAccount::class)
    fun getByCpfNotFound(){
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(false)


        clientServ.getByCpf("42511229846")


    }

    @Test
    fun getByCpfFound(){
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(true)
        Mockito.`when`(clientServ.clientRepository.findByCpf("42511229846")).thenReturn(client)

        clientServ.getByCpf("42511229846")

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).findByCpf("42511229846")
    }

    @Test(expected = ExceptionClientHasAccount::class)
    fun `exist client by cpf`(){
//        Mockito.`when`(clientServ.clientRepository.existsByCpf(client.cpf!!)).thenThrow(ExceptionClientHasAccount(1))
        clientServ.validateClient(client)

        Mockito.verify(clientServ.clientRepository, Mockito.times(1)).existsByCpf(client.cpf!!)
    }

    @Test(expected = MethodArgumentNotValidException::class)
    fun `client null`(){
        val clientNull = Client(1,null,"pedro@gmail.com","42511229846")
        Mockito.`when`(clientServ.clientRepository.save(clientNull)).thenThrow(MethodArgumentNotValidException::class.java)

        clientServ.createClient(clientNull)

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).save(clientNull)

    }


}