package com.zup.bank.dto.error

import java.lang.RuntimeException
import java.util.*

class ErrorException(

            var statusError: Int? = null,
            var timestamp: Date? = null,
            var warnings: String? = null,
            var field:String? = null,
            var errors: MutableList<String>? = null

):RuntimeException()
{

}

