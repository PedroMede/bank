package com.zup.bank.exception.customErrors

import java.util.*

class ResponseMethodNotValid(
    var statusError: Int,
    var warning: String,
    var fields: List<String>,
    var timestamp: Date
) {
}