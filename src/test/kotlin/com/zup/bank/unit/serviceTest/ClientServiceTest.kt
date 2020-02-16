package com.zup.bank.unit.serviceTest


import com.zup.bank.enum.ClientStatus
import com.zup.bank.exception.customErrors.ClientInProcessException
import com.zup.bank.exception.customErrors.ExceptionClientAlreadyReg
import com.zup.bank.model.Waitlist
import com.zup.bank.model.Client
import com.zup.bank.repository.WaitListRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.service.serviceImpl.ClientServImp
import org.camunda.bpm.engine.RuntimeService
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

            Mockito.mock(ClientRepository::class.java),
            Mockito.mock(WaitListRepository::class.java),
            Mockito.mock(RuntimeService::class.java)

    )

    @Mock
    lateinit var client: Client
    lateinit var clientB: Waitlist

    @Before
    fun createClient(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")

    }


    @Test(expected = ExceptionClientAlreadyReg::class)
    fun `client already exist in bank`(){
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(true)

        clientServ.startCamunda(client)

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).existsByCpf("42511229846")
    }

    @Test(expected = ClientInProcessException::class)
    fun `client still in process on Camunda`(){

        clientB = Waitlist(1,"42511229846",ClientStatus.PROCESSING)
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(false)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.findByCpfAndStatus("42511229846",client.status)).thenReturn(clientB)

        clientServ.startCamunda(client)

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1)).findByCpfAndStatus("42511229846",client.status)
    }

    @Test
    fun` star camunda successfully`(){
        clientB = Waitlist(1,"42511229846")
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(false)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.findByCpfAndStatus("42511229846",client.status)).thenReturn(clientB)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.existsByCpf("42511229846")).thenReturn(true)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.findByCpf("42511229846")).thenReturn(clientB)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.save(clientB)).thenReturn(clientB)

        clientServ.startCamunda(client)
        Assert.assertThat(clientB.status,CoreMatchers.`is`(ClientStatus.PROCESSING))

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1)).findByCpfAndStatus("42511229846",client.status)
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1)).findByCpf("42511229846")
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1)).save(clientB)

    }

    @Test
    fun `client that not exist in waitlist`(){
        clientB = Waitlist(1,"42511229846")
        Mockito.`when`(clientServ.clientRepository.existsByCpf("42511229846")).thenReturn(false)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.findByCpfAndStatus("42511229846",client.status)).thenReturn(clientB)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.existsByCpf("42511229846")).thenReturn(false)
        Mockito.`when`(clientServ.blackBlockedRepositoryRepo.save(clientB)).thenReturn(clientB)

        clientServ.startCamunda(client)
        Assert.assertThat(clientB.status,CoreMatchers.`is`(ClientStatus.BLOCKED))

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1)).findByCpfAndStatus("42511229846",client.status)
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(clientServ.blackBlockedRepositoryRepo,Mockito.times(1))
            .save(Mockito.any(Waitlist::class.java))
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

    @Test
    fun `delete by cpf`(){
        Mockito.doNothing().`when`(clientServ.clientRepository).deleteByCpf("42511229846")

        clientServ.deleteBycpf("42511229846")

        Mockito.verify(clientServ.clientRepository,Mockito.times(1)).deleteByCpf("42511229846")
    }

}