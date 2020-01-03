package com.zup.bank.controller.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(KotlinNullPointerException::class)
    fun handleNullpointer(e: KotlinNullPointerException ) : ResponseEntity<Any>{
        println(e)

        return ResponseEntity.badRequest().build()
    }
}