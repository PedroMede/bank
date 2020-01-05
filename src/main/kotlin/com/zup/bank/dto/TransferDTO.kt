package com.zup.bank.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class TransferDTO(

        @field:[NotBlank(message = "Conta de origem obrigatória")]
        var originAcc:String? = null,

        @field:[NotBlank(message = "Conta de destino obrigatória")]
        var destinyAcc:String? = null,

        @field:[NotNull(message = "Valor obrigatório")]
        var value:Double? = null
)
{
}