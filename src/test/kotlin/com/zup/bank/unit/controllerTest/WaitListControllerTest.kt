package com.zup.bank.unit.controllerTest

import com.zup.bank.controller.WaitlistController
import com.zup.bank.model.Waitlist
import com.zup.bank.service.ServiceWaitlist
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class WaitListControllerTest {

    private val waitlistController: WaitlistController= WaitlistController(
        Mockito.mock(ServiceWaitlist::class.java)
    )

    @Mock
    lateinit var client: Waitlist

    @Test
    fun `get by cpf `(){
        client = Waitlist(1,"42511229846")
        Mockito.`when`(waitlistController.waitlistService.findByCpf("42511229846")).thenReturn(client)
        waitlistController.getClientByCpf("42511229846")
        Mockito.verify(waitlistController.waitlistService,Mockito.times(1)).findByCpf("42511229846")
    }
}