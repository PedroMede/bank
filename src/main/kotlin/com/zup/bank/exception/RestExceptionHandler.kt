package com.zup.bank.exception

import com.zup.bank.dto.error.ErrorException
import com.zup.bank.dto.error.ResponseErrorExcep
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*


@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(KotlinNullPointerException::class)
    fun handleNullpointer(e: KotlinNullPointerException ) : ResponseEntity<Any>{
        println(e)

        return ResponseEntity.badRequest().build()
    }

    @ExceptionHandler(ErrorException::class)
    fun handleNotFound(exception: ErrorException) : ResponseEntity<ResponseErrorExcep>{
        var responseErrorExcep =
                ResponseErrorExcep(HttpStatus.NOT_FOUND.value(),Date(),"Cpf not exist","",null)

            return ResponseEntity(responseErrorExcep, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(ex: EmptyResultDataAccessException, request: WebRequest) : ResponseEntity<ResponseErrorExcep>{
        var responseErrorExcep =
                ResponseErrorExcep(HttpStatus.NOT_FOUND.value(),Date(),"Cpf or Account not exist","",null)

        return ResponseEntity(responseErrorExcep, HttpStatus.NOT_FOUND)
    }
}