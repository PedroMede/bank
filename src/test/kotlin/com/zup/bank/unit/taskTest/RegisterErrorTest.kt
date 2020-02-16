package com.zup.bank.unit.taskTest

import com.zup.bank.common.camunda.task.RegisterError
import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Waitlist
import com.zup.bank.model.Client
import com.zup.bank.service.ServiceWaitlist
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RegisterErrorTest {

    private val registerError = RegisterError(
        Mockito.mock(ServiceWaitlist::class.java)
    )


    @Mock
    lateinit var client: Client
    lateinit var clientB: Waitlist
    @Before
    fun setup(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        clientB = Waitlist(1,"42511229846",ClientStatus.PROCESSING)
    }

    @Test
    fun `Method delegate execute client in blacklist`() {
        val execution = Mockito.mock(DelegateExecution::class.java)
        val map = mutableMapOf<String, Any>()
        map["cpf"] = client.cpf!!

        Mockito.`when`(execution.variables).thenReturn(map)
        Mockito.`when`(registerError.serviceWaitlist.getByCpf(client.cpf!!,ClientStatus.PROCESSING)).thenReturn(clientB)
        Mockito.doNothing().`when`(registerError.serviceWaitlist).createBlocked(clientB)


        registerError.execute(execution)
        Assert.assertThat(clientB.status,CoreMatchers.`is`(ClientStatus.BLOCKED))

        Mockito.verify(registerError.serviceWaitlist,Mockito.times(1)).getByCpf(client.cpf!!,ClientStatus.PROCESSING)
        Mockito.verify(registerError.serviceWaitlist,Mockito.times(1)).createBlocked(clientB)

    }
}