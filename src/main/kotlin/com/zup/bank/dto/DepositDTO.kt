package com.zup.bank.dto

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class DepositDTO(

    @field:[NotBlank(message = "client.cpf.required")]
    @field:[CPF(message = "client.cpf.not.valid")]
    var cpf: String? = null,

    @field:[NotBlank(message = "account.number.required")]
    var numberAcc: String? = null,

    @field:[NotNull(message = "account.value")]
    var value: Double? = null
) {}