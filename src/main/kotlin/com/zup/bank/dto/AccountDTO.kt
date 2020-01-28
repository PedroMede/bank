package com.zup.bank.dto

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank


 class AccountDTO (

        @field:[NotBlank(message="client.cpf.required")]
        @field:[CPF(message="client.cpf.not.valid")]
        var cpf : String? = null
)
{}