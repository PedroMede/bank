package com.zup.bank.exception.customErrors

import com.zup.bank.exception.AllCodeErrors
import java.util.*

data class ResponseClientHasAcc(

    var statusError: Int,
    var warning: AllCodeErrors,
    var field : String,
    var timestamp: Date

) {

}