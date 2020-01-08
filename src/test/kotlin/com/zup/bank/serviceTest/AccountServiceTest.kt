package com.zup.bank.serviceTest

import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.serviceImpl.AccountServImpl
import com.zup.bank.service.serviceImpl.ClientServImp
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit4.SpringRunner

import java.util.*
import kotlin.Exception

@RunWith(SpringRunner::class)
class AccountServiceTest {

    private val accountServ: AccountServImpl = AccountServImpl(
            Mockito.mock(AccountRepository::class.java),
            Mockito.mock(ClientRepository::class.java),
            Mockito.mock(OperationsRepository::class.java)
    )



    @Mock
    lateinit var client: Client
    lateinit var operations: Operations
    lateinit var account: Account
    lateinit var account2: Account

    @Before
    fun `create variables that are used in Account Service`(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        account = Account(1,"0001","18", client,100.00,true)
        account2 = Account(1,"0001","19", client,100.00,false)
        operations = Operations(1,"DEPOSIT",50.00, Date(), account)
    }


    @Test
    fun `validate account when not exist account and client is register`(){
        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(false)
        Mockito.`when`(accountServ.clientRepository.existsByCpf(client.cpf!!)).thenReturn(true)

        accountServ.validateAccount(client.cpf!!)


        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.clientRepository,Mockito.times(1)).existsByCpf(client.cpf!!)
    }

    @Test(expected = Exception::class)
    fun `validate account when  exist account and client has account register and active equals true`(){
        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(true)
        Mockito.`when`(accountServ.accRepository.findByHolderCpf(client.cpf!!)).thenReturn(account)

        Assert.assertThat(true,CoreMatchers.`is`(account.active))

        accountServ.validateAccount(client.cpf!!)


        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).findByHolderCpf(client.cpf!!)
    }

    @Test
    fun `validate account when exist an account and client has account register and active equals false`(){
        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(true)
        Mockito.`when`(accountServ.accRepository.findByHolderCpf(client.cpf!!)).thenReturn(account2)
        Assert.assertThat(false,CoreMatchers.`is`(account2.active))
        Mockito.`when`(accountServ.clientRepository.existsByCpf(client.cpf!!)).thenReturn(true)

        accountServ.validateAccount(client.cpf!!)


        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).findByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.clientRepository,Mockito.times(1)).existsByCpf(client.cpf!!)
    }


    @Test(expected = Exception::class)
    fun `validate account when client is not registrated`(){
        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(false)
        Mockito.`when`(accountServ.clientRepository.existsByCpf(client.cpf!!)).thenReturn(false)

        accountServ.validateAccount(client.cpf!!)


        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.clientRepository,Mockito.times(1)).existsByCpf(client.cpf!!)
    }
}