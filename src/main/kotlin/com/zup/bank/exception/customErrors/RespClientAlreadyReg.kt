package com.zup.bank.exception.customErrors

import com.zup.bank.exception.AllCodeErrors
import java.util.*

class RespClientAlreadyReg(

    var statusError: Int,
    var warning: String,
    var field : String,
    var timestamp: Date
) {

}