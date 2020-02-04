package com.zup.bank.model

import javax.persistence.*


@Entity
@Table(name = "account")
class Account(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numAcc")
    var numAcc: Long? = null,

    @Column(name = "agency")
    var agency: String? = null,

    @Column(name = "numberAcc", unique = true)
    var numberAcc: String? = null,

    @OneToOne
    var holder: Client? = null,

    @Column(name = "balance")
    var balance: Double? = 0.0,

    @Column(name = "active")
    var active: Boolean? = true
) {
}