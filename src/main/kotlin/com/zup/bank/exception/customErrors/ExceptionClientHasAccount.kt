package com.zup.bank.exception.customErrors

import com.zup.bank.exception.AllCodeErrors
import java.util.*

class ExceptionClientHasAccount(

    statusError : Int,
    warning: AllCodeErrors,
    field:String,
    timestamp: Date = Date()

): GeneralException(statusError,warning,field,timestamp) {
}

