package com.zup.bank.integrated

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerOperation {

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val url: String = "http://localhost:8090/bankStatement"


    @Transactional
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `return a list of all operations `(){
        mockMvc.perform(MockMvcRequestBuilders
            .get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))

    }


    @Transactional
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `get bank statement `(){
        mockMvc.perform(MockMvcRequestBuilders
            .get("$url/11")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))

    }


}