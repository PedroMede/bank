package com.zup.bank.dto

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank


 class AccountDTO (

        @field:NotBlank(message="cpf obrigatório")
        @field:CPF(message="Cpf inválido")
        var cpf : String? = null
)
{}