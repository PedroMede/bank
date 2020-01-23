package com.zup.bank.integrated.controllerClient

import com.google.gson.Gson
import com.zup.bank.model.Client
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ControllerClient {

    @Autowired
    private lateinit var mockMvc: MockMvc
    private val url: String = "http://localhost:8090/client"

    @Test
    fun `function create user with invalid fields`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(Client(null,"","","")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusHttp").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.objectName").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.fields").isNotEmpty)

    }
}