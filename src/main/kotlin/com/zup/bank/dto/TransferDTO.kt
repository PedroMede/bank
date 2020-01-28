package com.zup.bank.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class TransferDTO(

        @field:[NotBlank(message = "account.origin")]
        var originAcc:String? = null,

        @field:[NotBlank(message = "account.destiny")]
        var destinyAcc:String? = null,

        @field:[NotNull(message = "account.value")]
        var value:Double? = null
)
{
}