package com.zup.bank.model

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "client")
class Client(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id : Long? = null,

        @NotBlank
        @Column(name = "name")
        var name: String? = null,

        @NotBlank
        @Column(name = "email")
        var email:String? = null,

        @NotBlank
        @Column(name = "cpf")
        var cpf: String? = null
){

}