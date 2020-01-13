package com.zup.bank.model

import com.zup.bank.enum.TypeOperation
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "operations")
class Operations(

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id")
     var id:Long? = null,

     @Enumerated(EnumType.STRING)
     var typeOp: TypeOperation? = null,


     @Column(name="value")
     var value:Double? = 0.0,
 //date
     @Column(name="date",columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
     var date: Date = Date(),

     @ManyToOne
     var account: Account? = null
)
{
}