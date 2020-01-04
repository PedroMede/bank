package com.zup.bank.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "operations")
class Operations(

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     var id:Long? = null,

     @Column(name = "typeOperation")
     var typeOp: String? = null,

     @Column(name="value")
     var value:Double? = 0.0,

     @Column(name="date")
     var date: String? = null,

     @ManyToOne
     var account: Account? = null
)
{
}