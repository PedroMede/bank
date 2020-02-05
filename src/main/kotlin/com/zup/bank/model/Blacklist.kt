package com.zup.bank.model

import javax.persistence.*

@Entity
@Table(name="blacklist")
class Blacklist (

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    var id: Long? = null,

    @Column(name="cpf")
    var cpf:String? = null

){

}