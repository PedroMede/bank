package com.zup.bank.dto

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class AccountDTO (

        @field: NotBlank(message="cpf obrigatório")
        @field: NotEmpty(message ="cpf obrigatório")
        @field: NotNull(message ="cpf obrigatório")
        @CPF(message="Cpf inválido")
        var cpf : String? = null
)
{}