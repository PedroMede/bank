package com.zup.bank.exception.customErrors

import com.zup.bank.exception.AllCodeErrors
import java.util.*

class NotSufficientBalanceException(
    statusError : Int,
    warning: AllCodeErrors,
    timestamp: Date = Date()
) : GeneralException(statusError,warning,"",timestamp) {
}