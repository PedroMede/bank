package com.zup.bank.model

import com.zup.bank.enum.ClientStatus
import org.hibernate.validator.constraints.br.CPF
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "client")
class Client(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        var id : Long? = null,

        @field: NotBlank(message = "client.name.required")
        @field: NotNull(message = "client.name.required")
        @field: NotEmpty(message = "client.name.required")
        @Column(name = "name")
        var name: String? = null,

        @field:NotBlank(message="client.email.required")
        @field:NotNull(message="client.email.required")
        @field:Email(message = "client.email.not.valid")
        @Column(name = "email")
        var email:String? = null,

        @field:NotBlank(message="client.cpf.required")
        @field:NotEmpty(message="client.cpf.required")
        @field:NotNull(message="client.cpf.required")
        @field:CPF(message = "client.cpf.not.valid")
        @Column(name = "cpf")
        var cpf: String? = null,

        @Column(name= "status")
        var status: ClientStatus = ClientStatus.PROCESSING
)