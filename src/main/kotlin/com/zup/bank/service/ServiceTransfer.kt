package com.zup.bank.service

import com.zup.bank.dto.ObjectKafka
import com.zup.bank.dto.TransferDTO
import com.zup.bank.dto.TransferDTOResponse
import com.zup.bank.model.Transfer


interface ServiceTransfer {

    fun transfer(opTransfer: ObjectKafka)
    fun postInKafka(opTransfer: TransferDTO) : TransferDTOResponse
    fun getById(id :Long) : Transfer
}