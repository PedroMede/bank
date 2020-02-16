package com.zup.bank.unit.taskTest

import com.zup.bank.common.camunda.task.CreateSuccess
import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Waitlist
import com.zup.bank.model.Client
import com.zup.bank.service.ServiceWaitlist
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class CreateSuccessTest {

    private val createSuccessTask = CreateSuccess(
        Mockito.mock(ServiceClient::class.java),
        Mockito.mock(ServiceWaitlist::class.java)
    )

    @Mock
    lateinit var client: Client
    lateinit var clientB: Waitlist

    @Before
    fun setup(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
        clientB = Waitlist(1,"42511229846")
    }

    @Test
    fun `Method delegate execute client in blacklist`() {
        val execution = Mockito.mock(DelegateExecution::class.java)
        val map = mutableMapOf<String, Any>()
        map["name"] = client.name!!
        map["email"] = client.email!!
        map["cpf"] = client.cpf!!

        Mockito.`when`(execution.variables).thenReturn(map)
        Mockito.doNothing().`when`(createSuccessTask.serviceClient).createClient(client)
        Mockito.`when`(createSuccessTask.servWaitlist.getByCpf(client.cpf!!,client.status)).thenReturn(clientB)
        Mockito.doNothing().`when`(createSuccessTask.servWaitlist).createBlocked(clientB)

        createSuccessTask.execute(execution)
        Assert.assertThat(clientB.status,CoreMatchers.`is`(ClientStatus.CREATED))

        Mockito.verify(createSuccessTask.servWaitlist,Mockito.times(1)).getByCpf(client.cpf!!,client.status)
        Mockito.verify(createSuccessTask.servWaitlist,Mockito.times(1)).createBlocked(clientB)
    }


}