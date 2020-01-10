package com.zup.bank.exception

import com.zup.bank.exception.customErrors.*
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*


@ControllerAdvice
class RestExceptionHandler(val message: Messages) {

    @ExceptionHandler(KotlinNullPointerException::class)
    fun handleNullpointer(e: KotlinNullPointerException ) : ResponseEntity<Any>{
        println(e)

        return ResponseEntity.badRequest().build()
    }

    @ExceptionHandler(ExceptionClientHasAccount::class)
    fun handleClientHasAccount(e: ExceptionClientHasAccount) : ResponseEntity<ResponseClientHasAcc>{
        val responseErrorExcep =
            ResponseClientHasAcc(e.statusError, e.warnings, e.field, e.timestamp)

            return ResponseEntity(responseErrorExcep, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(ExceptionClientAlreadyReg::class)
    fun handleClientAlreadyReg(e: ExceptionClientAlreadyReg) : ResponseEntity<RespClientAlreadyReg>{
        val responseErrorExcep =
            RespClientAlreadyReg(e.statusError, e.warnings, e.field, e.timestamp)

        return ResponseEntity(responseErrorExcep, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(e: EmptyResultDataAccessException) : ResponseEntity<ResponseEmptyResult>{
        val responseErrorExcep = ResponseEmptyResult(
            HttpStatus.NOT_FOUND.value(),
            AllCodeErrors.CODEACCOUNTREGISTERED,
            Date())

        return ResponseEntity(responseErrorExcep, HttpStatus.NOT_FOUND)
    }
}