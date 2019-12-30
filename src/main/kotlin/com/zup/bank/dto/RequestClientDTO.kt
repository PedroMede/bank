package com.zup.bank.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class RequestClientDTO(
        @NotBlank(message = "nome obrigatório")
        val name: String,

        @NotBlank(message = "email Obrigatório")
        @Email(message ="Email não é válido")
        val email: String,

        @NotBlank(message = "cpf obrigatório")
        val cpf: String

) {


}