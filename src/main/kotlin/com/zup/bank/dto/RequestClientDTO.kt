package com.zup.bank.dto

import javax.validation.constraints.NotBlank

data class RequestClientDTO(
        @NotBlank(message = "nome obrigatório")
        val name: String,

        @NotBlank(message = "email Obrigatório")
        val email: String,

        @NotBlank(message = "cpf obrigatório")
        val cpf: String

) {


}