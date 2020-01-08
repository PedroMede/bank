package com.zup.bank.dto.error

import java.util.*

data class ResponseErrorExcep(

        var statusError: Int,
        var timestamp: Date,
        var warnings: String,
        var field:String,
        var errors: MutableList<String>?

) {

}