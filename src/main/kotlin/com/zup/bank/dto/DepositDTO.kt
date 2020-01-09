package com.zup.bank.dto

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class DepositDTO ( @field:NotBlank(message = "cpf obrigatório")
                   @CPF(message = "cpf inválido")
                   var cpf: String? = null,

                   @field:NotBlank(message = "Número da conta obrigatório")
                   var numberAcc: String?  = null,

                   @field:NotNull(message = "Valor a ser depositado obrigatório")
                   var value: Double? = null)
{



}