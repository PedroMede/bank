package com.zup.bank.repository

import com.zup.bank.model.Client
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository : JpaRepository<Client,Long> {

}