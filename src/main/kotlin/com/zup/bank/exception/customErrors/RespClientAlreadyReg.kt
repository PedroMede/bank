package com.zup.bank.exception.customErrors

import java.util.*

class RespClientAlreadyReg(

    var statusError: Int,
    var warning: String,
    var field : String,
    var timestamp: Date
) {

}