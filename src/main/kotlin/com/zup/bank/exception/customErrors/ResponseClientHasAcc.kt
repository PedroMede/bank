package com.zup.bank.exception.customErrors

import java.util.*

data class ResponseClientHasAcc(

    var statusError: Int,
    var warning: String,
    var field : String,
    var timestamp: Date

) {

}