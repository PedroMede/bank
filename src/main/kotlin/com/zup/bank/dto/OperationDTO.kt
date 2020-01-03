package com.zup.bank.dto

import com.zup.bank.enum.TypeOperation
import com.zup.bank.model.Account
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

class OperationDTO {

    @NotBlank(message = "Operação obrigatória")
    var typeOp: TypeOperation? = null

    @NotBlank(message="Valor obrigatório")
    var value:Double? = 0.0


    var desc:String? =  null

    @NotBlank(message = "Data obrigatória")
    var date: Date?= null

    @NotBlank(message = "Número da conta obrigatória")
    var account: String? = null
}