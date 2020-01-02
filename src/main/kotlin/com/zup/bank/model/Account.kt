package com.zup.bank.model

import javax.persistence.*

@Entity
@Table(name = "account")
class Account(

        @Id
        @Column(name="numAcc")
        var numAcc: Long? = null,

        @Column(name="agency")
        var agency: String?= null,

        @OneToOne
        @Column(name = "holder")
        var holder: Client? = null,

        @Column(name= "balance")
        var balance: Double? = null

) {
}