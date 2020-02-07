package com.zup.bank.exception.customErrors

import java.util.*

class ClientInProcessException(
    statusError : Int,
    warning: String,
    timestamp: Date = Date()
): GeneralException(statusError,warning,"",timestamp)
{
}