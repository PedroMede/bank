package com.zup.bank.dto

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class RequestClientDTO(

        @field:NotBlank (message = "nome obrigatório")
        @field:NotNull(message = "nome obrigatório")
        @field:NotEmpty(message = "nome obrigatório")
        val name: String,

        @field: NotBlank(message = "email Obrigatório")
        @field: Email(message ="Email não é válido")
        @field:NotNull(message = "email obrigatório")
        @field:NotEmpty(message = "email obrigatório")
        val email: String,

        @field: NotBlank(message = "cpf obrigatório")
        @field:NotNull(message = "cpf obrigatório")
        @field:NotEmpty(message = "cpf obrigatório")
        @field:CPF(message = "cpf inválido")
        val cpf: String

) {


}