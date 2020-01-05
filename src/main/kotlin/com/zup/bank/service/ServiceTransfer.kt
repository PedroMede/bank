package com.zup.bank.service

import com.zup.bank.dto.TransferDTO
import com.zup.bank.model.Transfer


interface ServiceTransfer {

    fun transfer(opTransfer: TransferDTO): Transfer
}