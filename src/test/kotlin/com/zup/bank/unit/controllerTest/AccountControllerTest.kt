package com.zup.bank.unit.controllerTest


import com.zup.bank.controller.AccountController
import com.zup.bank.dto.AccountDTO
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.service.ServiceAcc
import org.junit.Before
import org.mockito.Mockito

class AccountControllerTest {
    private val accController : AccountController = AccountController(
        Mockito.mock(ServiceAcc:: class.java)
    )

    lateinit var accDTO: AccountDTO
    lateinit var account: Account
    lateinit var client : Client

    @Before
    fun create(){

        client  = Client(1,"Pedro","pedro@gmail.com","42511229846")
        account = Account(1,"0001","18", client,100.00,true)
        accDTO = AccountDTO("42511229846")
    }


}