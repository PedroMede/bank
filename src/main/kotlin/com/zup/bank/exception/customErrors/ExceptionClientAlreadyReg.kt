package com.zup.bank.exception.customErrors

import com.zup.bank.exception.AllCodeErrors
import java.util.*

class ExceptionClientAlreadyReg(

    statusError : Int,
    warning: AllCodeErrors,
    field: String,
    timestamp: Date = Date()

):GenaralException(statusError,warning,field,timestamp) {
}