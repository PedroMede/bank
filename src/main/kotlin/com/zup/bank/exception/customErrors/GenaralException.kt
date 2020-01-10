package com.zup.bank.exception.customErrors

import com.zup.bank.exception.AllCodeErrors
import java.lang.RuntimeException
import java.util.*

open class GenaralException(

    val statusError: Int,
    val warnings: AllCodeErrors,
    val field:String,
    val timestamp: Date = Date()

): RuntimeException() {
}