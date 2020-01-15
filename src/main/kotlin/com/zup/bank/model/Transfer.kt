package com.zup.bank.model

import com.zup.bank.enum.StatusTransfer
import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "transfer")
class Transfer (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name ="id")
        var id: Long?= null,

        @ManyToOne
        var originAcc: Account? = null,

        @ManyToOne
        var destinyAcc: Account? = null,

        @Column(name="value")
        var value:Double? = null,

        @Enumerated(EnumType.STRING)
        var status: StatusTransfer? = null
){
}