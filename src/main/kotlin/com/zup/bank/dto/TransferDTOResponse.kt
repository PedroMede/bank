package com.zup.bank.dto

import com.zup.bank.enum.StatusTransfer

data class TransferDTOResponse (

    var originAcc:String? = null,

    var destinyAcc:String? = null,

    var value:Double? = null,

    var status: StatusTransfer = StatusTransfer.PROCESSING
){
}