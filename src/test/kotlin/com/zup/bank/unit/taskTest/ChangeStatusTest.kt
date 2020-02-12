package com.zup.bank.unit.taskTest

import com.zup.bank.common.camunda.task.ChangeStatus
import com.zup.bank.model.Client
import com.zup.bank.repository.BlacklistRepository
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito


class ChangeStatusTest {

    private val changeStatus = ChangeStatus(
        Mockito.mock(BlacklistRepository::class.java)
    )

    @Mock
    lateinit var client: Client

    @Before
    fun setup(){
        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
    }

    @Test
    fun `Method delegate execute client in blacklist`(){
        val execution = Mockito.mock(DelegateExecution::class.java)
        val map = mutableMapOf<String,Any>()
        map["name"] = client.name!!
        map["email"] = client.email!!
        map["cpf"] = client.cpf!!

        Mockito.`when`(execution.variables).thenReturn(map)
        Mockito.`when`(changeStatus.blacklistRepo.existsByCpf("42511229846")).thenReturn(true)

        changeStatus.execute(execution)

        Mockito.verify(changeStatus.blacklistRepo,Mockito.times(1)).existsByCpf("42511229846")


    }

    @Test
    fun `Method delegate execute`(){
        val execution = Mockito.mock(DelegateExecution::class.java)
        val map = mutableMapOf<String,Any>()
        map["name"] = client.name!!
        map["email"] = client.email!!
        map["cpf"] = client.cpf!!

        Mockito.`when`(execution.variables).thenReturn(map)
        Mockito.`when`(changeStatus.blacklistRepo.existsByCpf("42511229846")).thenReturn(false)

        changeStatus.execute(execution)

        Mockito.verify(changeStatus.blacklistRepo,Mockito.times(1)).existsByCpf("42511229846")

    }

}