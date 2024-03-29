package com.zup.bank.integrated

import com.google.gson.Gson
import com.zup.bank.ConfigAbstract
import com.zup.bank.dto.AccountDTO
import com.zup.bank.dto.DepositDTO
import com.zup.bank.model.Client
import org.junit.Before
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional

@Transactional
class ControllerAccount: ConfigAbstract() {

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

    @Sql("/scripts/client.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `function create account sucess`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(AccountDTO("42511229846")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numAcc").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.agency").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.holder").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(0.0))

    }

    @Test
    fun `get account by cpf or account that not put on url`(){
        mockMvc.perform(MockMvcRequestBuilders
            .get(url)
            .content(Gson().toJson(AccountDTO("42511229846")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)

    }

    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `function get balance `(){
        mockMvc.perform(MockMvcRequestBuilders
            .get("$url/balance/11")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.numAcc").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.agency").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.holder").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").isBoolean)

    }


    @Test
    fun `get balance by cpf number account that not put on url`(){
        mockMvc.perform(MockMvcRequestBuilders
            .get("$url/balance/78")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)

    }


    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `return a list with all account`(){
        mockMvc.perform(MockMvcRequestBuilders
            .get("$url/getAll")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].numAcc").value(1))

    }


    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    fun `function to disable account `(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/disableAcc/02160795607")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numAcc").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.agency").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.holder").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").isBoolean)

    }

    @Test
    fun `function disable account without cpf in url`(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/disableAcc/23")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)

    }

    @Test
    fun `deposit with invalid fields`(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/deposit")
            .content(Gson().toJson(DepositDTO("","",20.0)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)
    }

    @Test
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `deposit with valid fields and sucess`(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/deposit")
            .content(Gson().toJson(DepositDTO("02160795607","11",20.0)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numAcc").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.agency").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.holder").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").isBoolean)
    }

    @Test
    fun `withdraw with invalid fields`(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/withdraw")
            .content(Gson().toJson(DepositDTO("","",20.0)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)
    }

    @Test
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `withdraw with valid fields and sucess`(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/withdraw")
            .content(Gson().toJson(DepositDTO("41076018033","12",20.0)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numAcc").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.agency").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.numberAcc").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.holder").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").isBoolean)
    }

    @Test
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `function create account with exception that client have account`(){
        mockMvc.perform(MockMvcRequestBuilders
            .post(url)
            .content(Gson().toJson(AccountDTO("02160795607")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").value(422))
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.field").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)
    }

    @Test
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `withdraw that balance is not sufficient`(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/withdraw")
            .content(Gson().toJson(DepositDTO("02160795607","11",100.0)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").value(422))
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)
    }

    @Test
    @Sql("/scripts/account.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `withdraw that account and client are divergent`(){
        mockMvc.perform(MockMvcRequestBuilders
            .put("$url/withdraw")
            .content(Gson().toJson(DepositDTO("02160795607","13",100.0)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
            .andExpect(MockMvcResultMatchers.jsonPath("$.statusError").value(422))
            .andExpect(MockMvcResultMatchers.jsonPath("$.warning").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").isNotEmpty)
    }

}