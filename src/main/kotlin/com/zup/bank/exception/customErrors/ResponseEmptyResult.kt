package com.zup.bank.exception.customErrors

import com.zup.bank.exception.AllCodeErrors
import java.util.*

class ResponseEmptyResult
(
    var statusError: Int,
    var warning: AllCodeErrors,
    var timestamp: Date
) {
}