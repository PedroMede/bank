package com.zup.bank.integrated

import com.google.gson.Gson
import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.TransferDTO
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
class ControllerTransfer {

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val url: String = "http://localhost:8090/transfer"


    @Test
    fun `post in kafka with fields invalids`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(TransferDTO("","",500.00)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.fields").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)
    }

    @Transactional
    @Sql("/scripts/account.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `post in kafka with sucess`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(TransferDTO("12","11",5.00)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.jsonPath("$.originAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.destinyAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.value").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").isNotEmpty)
    }



}