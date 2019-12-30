package com.zup.bank.model

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "client")
class Client(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id : Long? = null,

        @NotBlank(message = "nome obrigatório")
        @Column(name = "name")
        var name: String? = null,

        @NotBlank(message="email obrigatório")
        @Column(name = "email")
        @Email(message = "email inválido")
        var email:String? = null,

        @NotBlank(message="cpf obrigatório")
        @Column(name = "cpf")
        var cpf: String? = null
){

}