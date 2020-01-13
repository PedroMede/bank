package com.zup.bank.serviceTest


import com.zup.bank.exception.customErrors.ExceptionClientAlreadyReg
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
import org.springframework.dao.EmptyResultDataAccessException
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
    @Test(expected = ExceptionClientAlreadyReg::class)
    fun `test if exist client registered`(){
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(true)

        clientServ.createClient(client)

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).existsByCpf(client.cpf!!)
    }

    @Test
    fun `not exist client and create a new one`(){
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(false)
        Mockito.`when`(clientServ.clientRepository.save(client)).thenReturn(client)

        val response : Client = clientServ.createClient(client)

        Assert.assertEquals(response, client)
        Assert.assertThat(response, CoreMatchers.notNullValue())
        Assert.assertThat(response.id, CoreMatchers.`is`(1L))


        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).save(client)
        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).existsByCpf(client.cpf!!)
    }

    @Test
    fun getAllClientOk(){
        Mockito.`when`(clientServ.clientRepository.findAll()).thenReturn(mutableListOf(client))

        clientServ.getAllClient()

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).findAll()
    }


    @Test(expected = EmptyResultDataAccessException::class)
    fun getByCpfNotFound(){
        Mockito.`when`(clientServ.clientRepository.findByCpf("88804879653")).thenThrow(EmptyResultDataAccessException::class.java)


        clientServ.getByCpf("88804879653")

    }

    @Test
    fun `Method get client by cpf working sucessfuly`(){

        Mockito.`when`(clientServ.clientRepository.findByCpf("42511229846")).thenReturn(client)

        clientServ.getByCpf("42511229846")


        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).findByCpf(client.cpf!!)
    }

    @Test(expected = ExceptionClientAlreadyReg::class)
    fun `exist client by cpf`(){
        Mockito.`when`(clientServ.clientRepository.existsByCpf(client.cpf!!)).thenReturn(true)

        clientServ.validateClient(client)

        Mockito.verify(clientServ.clientRepository, Mockito.times(1)).existsByCpf(client.cpf!!)
    }

    @Test(expected = MethodArgumentNotValidException::class)
    fun `client with field name is  null`(){
        val clientNull = Client(1,null,"pedro@gmail.com","42511229846")
        Mockito.`when`(clientServ.clientRepository.save(clientNull)).thenThrow(MethodArgumentNotValidException::class.java)

        clientServ.createClient(clientNull)

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).save(clientNull)

    }

    @Test(expected = MethodArgumentNotValidException::class)
    fun `client with field email is  null`(){
        val clientNull = Client(1,"Pedro",null,"42511229846")
        Mockito.`when`(clientServ.clientRepository.save(clientNull)).thenThrow(MethodArgumentNotValidException::class.java)

        clientServ.createClient(clientNull)

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).save(clientNull)

    }


}