package com.zup.bank.workflow


import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Client
import com.zup.bank.repository.BlacklistRepository
import com.zup.bank.service.ServiceBlackBlocked
import com.zup.bank.service.ServiceClient
import org.camunda.bpm.engine.test.Deployment
import org.camunda.bpm.engine.test.ProcessEngineRule
import org.camunda.bpm.engine.test.ProcessEngineTestCase
import org.camunda.bpm.scenario.ProcessScenario
import org.camunda.bpm.scenario.Scenario
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@RunWith(SpringRunner::class)
@Deployment(resources = ["diagram_test.bpmn"])
class CamundaTest {

    @Autowired
    lateinit var blacklistRepo: BlacklistRepository

    @Autowired
    lateinit var serviceClient: ServiceClient

    @Autowired
    lateinit var servBlackBlocked: ServiceBlackBlocked

    @Mock
    lateinit var process: ProcessScenario

    @Mock
    lateinit var client: Client

    @Before
    fun setup() {
        client = Client(1, "Pedro", "pedro@gmail.com", "42511229846", ClientStatus.PROCESSING)

    }

    @Transactional
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
    fun `function that stop in callback user task`() {

        val variables = setupVariables(client)



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