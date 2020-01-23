package com.zup.bank.integrated

import com.google.gson.Gson
import com.zup.bank.dto.AccountDTO
import com.zup.bank.model.Account
import com.zup.bank.model.Client
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
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
class ControllerAccount {


    @Autowired
    private lateinit var mockMvc: MockMvc
    private val url: String = "http://localhost:8090/account"
    private lateinit var client: Client

    @Before
    fun `create client`(){
        client = Client(1L,"Pedro", "pedro@gamil.com","42511229846")
    }

    @Test
    fun `function create account with invalid fields`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(AccountDTO("")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)

    }

    @Transactional
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `function create account sucess`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(AccountDTO("42511229846")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numAcc").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.agency").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.holder").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(0.0))

    }


}