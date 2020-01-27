package com.zup.bank.exception.customErrors

import java.util.*

class TransferToSameAccException(
    statusError : Int,
    warning: String,
    field: String,
    timestamp: Date = Date()

): GeneralException(statusError,warning,field,timestamp) {
}