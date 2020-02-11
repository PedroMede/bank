package com.zup.bank.integrated

import com.google.gson.Gson
import com.zup.bank.ConfigAbstract
import com.zup.bank.model.Client
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@Transactional
class ControllerClient: ConfigAbstract() {
    private val url: String = "http://localhost:8090/client"

    @Test
    fun `function create user with invalid fields`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(Client(null,"","pedro@gmail.com","42511229846")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)

    }

    @Test
    fun `get user by cpf that not put on url`(){
        mockMvc.perform(MockMvcRequestBuilders
            .get(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)

    }

    @Sql("/scripts/client.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `return a list with all clients`(){
        mockMvc.perform(MockMvcRequestBuilders
            .get("$url/getAll")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(2))
    }


    @Test
    fun `creat a client into table client`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(Client(null,"Pedro","pedro@gmail.com","42511229846")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").isString)
    }




}