package com.zup.bank.model

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
        @Column(name = "name")
        var name: String? = null,

        @NotBlank(message="email obrigatório")
        @Column(name = "email")
        @Email(message = "email inválido")
        var email:String? = null,

        @NotBlank(message="cpf obrigatório")
        @NotEmpty
        @NotNull
        @Column(name = "cpf")
        var cpf: String? = null
)