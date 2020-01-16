package com.zup.bank.exception.customErrors

import java.util.*

class ExceptionClientHasAccount(

    statusError : Int,
    warning: String,
    field:String,
    timestamp: Date = Date()

): GeneralException(statusError,warning,field,timestamp) {
}

