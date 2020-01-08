package com.zup.bank.dto.responseError

import org.springframework.beans.BeanUtils
import java.lang.RuntimeException
import java.util.*

class ErrorException(

            var statusError: Int? = null,
            var timestamp: Date? = null,
            var warnings: String? = null

):RuntimeException()
{

}

