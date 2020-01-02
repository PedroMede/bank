package com.zup.bank.model

import org.hibernate.validator.constraints.br.CPF
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "client")
class Client(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id : Long? = null,

        @field: NotBlank(message = "nome obrigatório")
        @field: NotNull(message = "nome obrigatório")
        @field: NotEmpty(message = "nome obrigatório")
        @Column(name = "name")
        var name: String? = null,

        @field:NotBlank(message="email obrigatório")
        @field:NotNull(message="email obrigatório")
        @field:Email(message = "email inválido")
        @Column(name = "email")
        var email:String? = null,

        @field:NotBlank(message="cpf obrigatório")
        @field:NotEmpty(message="cpf obrigatório")
        @field:NotNull(message="cpf obrigatório")
        @field:CPF(message = "cpf inválido")
        @Column(name = "cpf")
        var cpf: String? = null
)