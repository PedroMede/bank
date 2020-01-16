package com.zup.bank.exception.customErrors

import java.util.*

class ResponseEmptyResult
(
    var statusError: Int,
    var warning: String,
    var timestamp: Date
) {
}