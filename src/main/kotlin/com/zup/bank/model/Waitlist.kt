package com.zup.bank.model

import com.zup.bank.enum.ClientStatus
import javax.persistence.*

@Entity
@Table(name="waitlist")
class Waitlist(
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="id")
    var id : Long? = null,

    @Column(name="cpf")
    var cpf: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    var status: ClientStatus = ClientStatus.BLOCKED
) {
    constructor (): this(null){}
}