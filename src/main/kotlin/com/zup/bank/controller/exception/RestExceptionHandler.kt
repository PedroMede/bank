package com.zup.bank.controller.exception

import com.zup.bank.dto.responseError.ErrorException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.Exception
import java.util.*
import javax.validation.ConstraintViolationException


@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(KotlinNullPointerException::class)
    fun handleNullpointer(e: KotlinNullPointerException ) : ResponseEntity<Any>{
        println(e)

        return ResponseEntity.badRequest().build()
    }

    @ExceptionHandler(ErrorException::class)
    fun handleNotFound(exception: ErrorException) : ResponseEntity<*>{

            exception.statusError = HttpStatus.NOT_FOUND.value()
            exception.timestamp  = Date()
            exception.warnings = "Not found"

            return ResponseEntity(exception, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(exception: EmptyResultDataAccessException) : ResponseEntity<Any>{

        var excep = ErrorException(HttpStatus.NOT_FOUND.value(),Date(),"Not Found")

        return ResponseEntity(excep, HttpStatus.NOT_FOUND)
    }
}