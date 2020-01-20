package com.zup.bank.repository

import com.zup.bank.model.Transfer
import org.springframework.data.jpa.repository.JpaRepository

interface TransferRepository : JpaRepository<Transfer,Long> {



}