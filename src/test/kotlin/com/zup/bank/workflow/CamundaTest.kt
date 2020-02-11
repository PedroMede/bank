package com.zup.bank.workflow


import com.zup.bank.ConfigAbstract
import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Client
import com.zup.bank.repository.BlacklistRepository
import com.zup.bank.service.ServiceBlackBlocked
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests
import org.camunda.bpm.scenario.ProcessScenario
import org.camunda.bpm.scenario.Scenario
import org.camunda.bpm.scenario.act.UserTaskAction
import org.camunda.bpm.scenario.delegate.TaskDelegate
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional

@Transactional
@Deployment(resources = ["diagram_test.bpmn"])
class CamundaTest : ConfigAbstract() {
    @Autowired
    lateinit var blacklistRepo: BlacklistRepository

    @Autowired
    lateinit var serviceClient: ServiceClient

    @Autowired
    lateinit var servBlackBlocked: ServiceBlackBlocked

    @Mock
    lateinit var process: ProcessScenario
    lateinit var exe: JavaDelegate

    @Mock
    lateinit var task:TaskDelegate

    @Mock
    lateinit var client: Client

    @Before
    fun setup() {
        client = Client(1, "Pedro", "pedro@gmail.com", "42511229846", ClientStatus.PROCESSING)

    }

    @Test
    @Sql("/scripts/waitlist.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `change status task working sucessfully`() {
        val variables = setupVariables(client)

        Scenario.run(process).startByKey("RegisterClient", variables).execute()

        Mockito.verify(process, Mockito.times(1)).hasFinished("EndEvent_Success")

        val clientP = serviceClient.getByCpf(client.cpf!!)
        Assert.assertThat(clientP.status, CoreMatchers.`is`(ClientStatus.CREATED))
    }

    @Test
    @Sql("/scripts/blacklist.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `function that stop in callback user task and accepted by user`() {
        val clientInblacklist = Client(2,"Lucia", "lucia@gmail.com","88804879653")
        val variables = setupVariables(clientInblacklist)

        Mockito.`when`(process.waitsAtUserTask("CALLBACK")).thenReturn(
            UserTaskAction {
                BpmnAwareTests.runtimeService().setVariable(it.executionId, "approved", true)
                it.complete()
            }
        )

        Scenario.run(process).startByKey("RegisterClient", variables).execute()

        Mockito.verify(process, Mockito.times(1)).hasFinished("EndEvent_Success")

    }

    @Test
    @Sql("/scripts/blacklist.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `function that stop in callback user task and blocked by user`() {
        val clientInblacklist = Client(2,"Lucia", "lucia@gmail.com","88804879653")
        val variables = setupVariables(clientInblacklist)

        Mockito.`when`(process.waitsAtUserTask("CALLBACK")).thenReturn(
            UserTaskAction {
                BpmnAwareTests.runtimeService().setVariable(it.executionId, "approved", false)
                it.complete()
            }
        )

        Scenario.run(process).startByKey("RegisterClient", variables).execute()

        Mockito.verify(process, Mockito.times(1)).hasFinished("EndEvent_Error")

    }

    private fun setupVariables(client: Client): MutableMap<String, Any> {
        val variables = mutableMapOf<String, Any>()
        variables["name"] = client.name!!
        variables["email"] = client.email!!
        variables["cpf"] = client.cpf!!
        variables["status"] = client.status!!
        return variables
    }

}