package com.zup.bank.model

import org.hibernate.validator.constraints.br.CPF
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name="blacklist")
class Blacklist (

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    var id: Long? = null,

    @field:[NotBlank(message = "client.cpf.required")]
    @field:[CPF(message="client.cpf.not.valid")]
    @Column(name="cpf")
    var cpf:String? = null

){

}