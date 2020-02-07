package com.zup.bank.unit.serviceTest


import com.zup.bank.exception.customErrors.ExceptionClientAlreadyReg
import com.zup.bank.model.Blacklist
import com.zup.bank.model.BlockedClient
import com.zup.bank.repository.BlacklistRepository
import com.zup.bank.service.serviceImpl.BlackListServImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class BlackListTest {

    private val servBlacklist: BlackListServImpl = BlackListServImpl(
        Mockito.mock(BlacklistRepository::class.java))


    @Mock
    lateinit var clientB: Blacklist

    @Before
    fun `create`(){
        clientB = Blacklist(1,"42511229846")
    }

    @Test
    fun `test create in black list`(){

        Mockito.`when`(servBlacklist.serviceBlack.existsByCpf("42511229846")).thenReturn(false)
        Mockito.`when`(servBlacklist.serviceBlack.save(clientB)).thenReturn(clientB)

        servBlacklist.createBlack(clientB)

        Mockito.verify(servBlacklist.serviceBlack,Mockito.times(1)).existsByCpf("42511229846")
        Mockito.verify(servBlacklist.serviceBlack,Mockito.times(1)).save(clientB)
    }

    @Test
    fun `test get all blocked list `(){
        Mockito.`when`(servBlacklist.serviceBlack.findAll()).thenReturn(mutableListOf(clientB))

        servBlacklist.getAll()

        Mockito.verify(servBlacklist.serviceBlack,Mockito.times(1)).findAll()
    }


    @Test
    fun `test delete`(){
        Mockito.doNothing().`when`(servBlacklist.serviceBlack).deleteByCpf("42511229846")

        servBlacklist.deleteByCpf("42511229846")

        Mockito.verify(servBlacklist.serviceBlack,Mockito.times(1)).deleteByCpf("42511229846")
    }

    @Test
    fun `function delete all black list`(){

        Mockito.doNothing().`when`(servBlacklist.serviceBlack).deleteAll()

        servBlacklist.deleteAll()

        Mockito.verify(servBlacklist.serviceBlack,Mockito.times(1)).deleteAll()
    }

    @Test(expected = ExceptionClientAlreadyReg::class)
    fun `test create in black list that already exist`(){

        Mockito.`when`(servBlacklist.serviceBlack.existsByCpf("42511229846")).thenReturn(true)

        servBlacklist.createBlack(clientB)

        Mockito.verify(servBlacklist.serviceBlack,Mockito.times(1)).existsByCpf("42511229846")

    }

}