package com.zup.bank.unit.serviceTest

import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.enum.TypeOperation
import com.zup.bank.exception.customErrors.AccountAndClientDivergentException
import com.zup.bank.exception.customErrors.ExceptionClientHasAccount
import com.zup.bank.exception.customErrors.NotSufficientBalanceException
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import com.zup.bank.model.Operations
import com.zup.bank.repository.AccountRepository
import com.zup.bank.repository.ClientRepository
import com.zup.bank.repository.OperationsRepository
import com.zup.bank.service.serviceImpl.AccountServImpl
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.dao.EmptyResultDataAccessException
import java.lang.NullPointerException

import java.util.*

class AccountServiceTest {

    private val accountServ: AccountServImpl = AccountServImpl(
            Mockito.mock(AccountRepository::class.java),
            Mockito.mock(ClientRepository::class.java),
            Mockito.mock(OperationsRepository::class.java)
    )

    @Mock
    lateinit var client: Client
    lateinit var operations: Operations
    lateinit var operations2: Operations
    lateinit var account: Account
    lateinit var account2: Account
    lateinit var nullAccount: Account
    lateinit var accountDTO: AccountDTO
    lateinit var depositDTO: DepositDTO

    @Before
    fun `create variables that are used in Account Service`(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        account = Account(1,"0001","18", client,100.00,true)
        account2 = Account(1,"0001","19", client,100.00,false)
        operations = Operations(1,TypeOperation.DEPOSIT,50.00, Date(), account)
        operations2 = Operations(1,TypeOperation.WITHDRAW,50.00, Date(), account)
        accountDTO = AccountDTO("42511229846")
        depositDTO = DepositDTO("42511229846","18",20.00)
    }


    @Test
    fun `validate account when not exist account and client is register`(){
        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(false)


        accountServ.validateAccount(client.cpf!!)


        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)

    }

    @Test(expected = ExceptionClientHasAccount::class)
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

        accountServ.validateAccount(client.cpf!!)


        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).findByHolderCpf(client.cpf!!)

    }


    @Test
    fun `reative account that returns empty account`(){


        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(false)

        nullAccount  = accountServ.reactivateAcc(client.cpf!!)

        Assert.assertThat(null,CoreMatchers.`is`(nullAccount.numberAcc))
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)
    }

    @Test
    fun `reative account that returns account reactivated`(){


        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(true)
        Mockito.`when`(accountServ.accRepository.findByHolderCpf(client.cpf!!)).thenReturn(account2)
        Mockito.`when`(accountServ.accRepository.save(account2)).thenReturn(account2)


         account2 = accountServ.reactivateAcc(client.cpf!!)

        Assert.assertEquals(true,account2.active)

        Mockito.verify(accountServ.accRepository,Mockito.times(1)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).findByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).save(account2)
    }


    @Test
    fun `function create account thats returns account reactivated`(){


        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(false)

        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(true)
        Mockito.`when`(accountServ.accRepository.findByHolderCpf(client.cpf!!)).thenReturn(account2)
        Mockito.`when`(accountServ.accRepository.save(account2)).thenReturn(account2)


        account = accountServ.createAcc(accountDTO)

        Assert.assertEquals(1L,account.numAcc)

        Mockito.verify(accountServ.accRepository,Mockito.times(2)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(2)).findByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).save(account)
    }


    @Test
    fun `create a new a account that client is register`(){

        Mockito.`when`(accountServ.accRepository.existsByHolderCpf(client.cpf!!)).thenReturn(false)

        Mockito.`when`(accountServ.clientRepository.findByCpf(client.cpf!!)).thenReturn(client)
        Mockito.`when`(accountServ.accRepository.save(Mockito.any(Account::class.java))).thenReturn(account)


        account = accountServ.createAcc(accountDTO)
        Assert.assertThat(account.numberAcc,CoreMatchers.notNullValue())

        Mockito.verify(accountServ.accRepository,Mockito.times(2)).existsByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.clientRepository,Mockito.times(1)).findByCpf(client.cpf!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).save(account)
    }


    @Test
    fun `get all account`(){

            Mockito.`when`(accountServ.accRepository.findAll()).thenReturn(mutableListOf(account))

            accountServ.getAllAcc()

            Mockito.verify(accountServ.accRepository,Mockito.times(1)).findAll()

    }

    @Test
    fun `disable account`(){

        Mockito.`when`(accountServ.accRepository.findByHolderCpfOrNumberAcc(account.holder!!.cpf!!,account.numberAcc!!))
                .thenReturn(null)

        accountServ.getByCpfOrNumberAcc(account.holder!!.cpf!!,account.numberAcc!!)


        Mockito.verify(accountServ.accRepository,Mockito.times(1))
                .findByHolderCpfOrNumberAcc(account.holder!!.cpf!!,account.numberAcc!!)

    }

    @Test(expected = NullPointerException::class)
    fun `disable account ok`(){

        Mockito.`when`(accountServ.accRepository.findByHolderCpfOrNumberAcc("42511229846",account.numberAcc!!))
                .thenReturn(account)
        Mockito.`when`(accountServ.accRepository.save(account)).thenReturn(account)


        accountServ.disableAcc("42511229846")


        Mockito.verify(accountServ.accRepository,Mockito.times(1))
                .findByHolderCpfOrNumberAcc(account.holder!!.cpf!!,account.numberAcc!!)
        Mockito.verify(accountServ.accRepository,Mockito.times(1))
            .save(account)
    }



    @Test
    fun `deposit in account`() {


        Mockito.`when`(accountServ.accRepository.findByHolderCpf(account.holder!!.cpf!!)).thenReturn(account)
        Mockito.`when`(accountServ.operationRepository.save(Mockito.any(Operations::class.java))).thenReturn(operations)
        Mockito.`when`(accountServ.accRepository.save(account)).thenReturn(account)

        accountServ.deposit(depositDTO)
        Assert.assertEquals(TypeOperation.DEPOSIT,operations.typeOp)

        Mockito.verify(accountServ.accRepository,Mockito.times(2)).findByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.operationRepository,Mockito.times(1)).save(Mockito.any(Operations::class.java))
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).save(account)

    }

    @Test(expected = AccountAndClientDivergentException::class)
    fun `account and client divergent in method validate fields`(){
        val account = Account(1,"0001","22", client,100.00,true)
        Mockito.`when`(accountServ.accRepository.findByHolderCpf(this.account.holder?.cpf!!)).thenReturn(account)

        accountServ.validateFields(this.account.numberAcc!!,this.account.holder?.cpf!!)

        Mockito.verify(accountServ.accRepository,Mockito.times(1)).findByHolderCpf(account.holder?.cpf!!)

    }

    @Test
    fun `withdraw in account`() {

        Mockito.`when`(accountServ.accRepository.findByHolderCpf(account.holder!!.cpf!!)).thenReturn(account)
        Mockito.`when`(accountServ.operationRepository.save(Mockito.any(Operations::class.java))).thenReturn(operations2)
        Mockito.`when`(accountServ.accRepository.save(account)).thenReturn(account)

        accountServ.withdraw(depositDTO)
        Assert.assertEquals(TypeOperation.WITHDRAW,operations2.typeOp)

        Mockito.verify(accountServ.accRepository,Mockito.times(2)).findByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.operationRepository,Mockito.times(1)).save(Mockito.any(Operations::class.java))
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).save(account)

    }

    @Test(expected = NotSufficientBalanceException::class)
    fun `withdraw in account with not sufficient balance`() {
        val withdraw = DepositDTO("42511229846","18",200.00)
        Mockito.`when`(accountServ.accRepository.findByHolderCpf(account.holder!!.cpf!!)).thenReturn(account)
        Mockito.`when`(accountServ.operationRepository.save(Mockito.any(Operations::class.java))).thenReturn(operations2)
        Mockito.`when`(accountServ.accRepository.save(account)).thenReturn(account)

        accountServ.withdraw(withdraw)
        Assert.assertEquals(TypeOperation.WITHDRAW,operations2.typeOp)

        Mockito.verify(accountServ.accRepository,Mockito.times(2)).findByHolderCpf(client.cpf!!)
        Mockito.verify(accountServ.operationRepository,Mockito.times(1)).save(Mockito.any(Operations::class.java))
        Mockito.verify(accountServ.accRepository,Mockito.times(1)).save(account)

    }


    @Test
    fun `banlace ok`(){
        Mockito.`when`(accountServ.accRepository.findByNumberAcc(account.numberAcc!!)).thenReturn(account)

        accountServ.balance(account.numberAcc!!)

        Mockito.verify(accountServ.accRepository,Mockito.times(1)).findByNumberAcc(account.numberAcc!!)
    }

    @Test(expected = EmptyResultDataAccessException::class)
    fun `banlance not found account`(){
        Mockito.`when`(accountServ.accRepository.findByNumberAcc(account.numberAcc!!))
            .thenThrow(EmptyResultDataAccessException::class.java)

        accountServ.balance(account.numberAcc!!)
    }

}