package com.zup.bank.unit.controllerTest


import com.zup.bank.controller.AccountController
import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.service.ServiceAcc
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AccountControllerTest {
    private val accController : AccountController = AccountController(
        Mockito.mock(ServiceAcc:: class.java)
    )

    lateinit var accDTO: AccountDTO
    lateinit var account: Account
    lateinit var accountDisabled: Account
    lateinit var client : Client
    lateinit var depositDTO: DepositDTO

    @Before
    fun create(){

        client  = Client(1,"Pedro","pedro@gmail.com","42511229846")
        account = Account(1,"0001","18", client,100.00,true)
        accountDisabled = Account(2,"0001","19", client,100.00,false)
        accDTO = AccountDTO("42511229846")
        depositDTO = DepositDTO("42511229846","18",20.00)
    }

    @Test
    fun `get account by cpf or number working`(){
        Mockito.`when`(accController.accountService.getByCpfOrNumberAcc("42511229846","")).thenReturn(account)
        accController.getByCpfOrNumberAcc("42511229846","")
        Assert.assertEquals("42511229846",account.holder?.cpf)
        Mockito.verify(accController.accountService,Mockito.times(1))
            .getByCpfOrNumberAcc("42511229846","")
    }

    @Test
    fun `create account ok`(){
        Mockito.`when`(accController.accountService.createAcc(accDTO)).thenReturn(account)
        accController.createAcc(accDTO)
        Assert.assertEquals(1L,account.numAcc)
        Mockito.verify(accController.accountService,Mockito.times(1))
            .createAcc(accDTO)
    }

    @Test
    fun `get by number acc to show balance`(){
        Mockito.`when`(accController.accountService.balance("18")).thenReturn(account)
        accController.getByNumberAcc("18")
        Assert.assertEquals("42511229846",account.holder?.cpf)
        Mockito.verify(accController.accountService,Mockito.times(1))
            .balance("18")
    }

    @Test
    fun `get all accounts`(){
        Mockito.`when`(accController.accountService.getAllAcc()).thenReturn(mutableListOf(account))
        accController.getAllAcc()
        Assert.assertEquals(mutableListOf(account), mutableListOf(account))
        Mockito.verify(accController.accountService,Mockito.times(1)).getAllAcc()
    }

    @Test
    fun `disable account working`(){
        Mockito.`when`(accController.accountService.disableAcc("42511229846")).thenReturn(accountDisabled)
        accController.disabledAcc("42511229846")
        Assert.assertEquals(false,accountDisabled.active)
        Mockito.verify(accController.accountService,Mockito.times(1))
            .disableAcc("42511229846")
    }

    @Test
    fun `do deposit`(){
        Mockito.`when`(accController.accountService.deposit(depositDTO)).thenReturn(account)
        accController.deposit(depositDTO)
        Assert.assertEquals(100.00,account.balance)
        Mockito.verify(accController.accountService,Mockito.times(1))
            .deposit(depositDTO)
    }

    @Test
    fun `do withdraw`(){
        Mockito.`when`(accController.accountService.withdraw(depositDTO)).thenReturn(account)
        accController.withdraw(depositDTO)
        Assert.assertEquals(100.00,account.balance)
        Mockito.verify(accController.accountService,Mockito.times(1))
            .withdraw(depositDTO)
    }
}