package com.zup.bank.exception

import com.zup.bank.common.AllCodeErrors
import com.zup.bank.common.Messages
import com.zup.bank.exception.customErrors.*
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*


@ControllerAdvice
class RestExceptionHandler(val message: Messages) {


    @ExceptionHandler(ExceptionClientHasAccount::class)
    fun handleClientHasAccount(e: ExceptionClientHasAccount) : ResponseEntity<ResponseClientHasAcc>{
        val responseErrorExcep =
            ResponseClientHasAcc(e.statusError, message.getMessageCode(e.warnings), e.field, e.timestamp)

            return ResponseEntity(responseErrorExcep, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(ExceptionClientAlreadyReg::class)
    fun handleClientAlreadyReg(e: ExceptionClientAlreadyReg) : ResponseEntity<RespClientAlreadyReg>{
        val responseErrorExcep =
            RespClientAlreadyReg(e.statusError, message.getMessageCode(e.warnings), e.field, e.timestamp)

        return ResponseEntity(responseErrorExcep, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(e: EmptyResultDataAccessException) : ResponseEntity<ResponseEmptyResult>{
        val responseErrorExcep = ResponseEmptyResult(
            HttpStatus.NOT_FOUND.value(),
            message.getMessageCode(AllCodeErrors.CPFNOTFOUND.code),
            Date())

        return ResponseEntity(responseErrorExcep, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ResponseMethodNotValid>{
        val errors : MutableList<String> = mutableListOf()

        e.bindingResult.fieldErrors.forEach{
            errors.add("${it.field}: ${it.defaultMessage}")
        }


        val responseErrorExcep = ResponseMethodNotValid(
            HttpStatus.BAD_REQUEST.value(),
            message.getMessageCode(AllCodeErrors.ILLEGALARGUMENT.code),
            errors,
            Date())

        return ResponseEntity(responseErrorExcep,HttpStatus.BAD_REQUEST)
   }

    @ExceptionHandler(NotSufficientBalanceException::class)
    fun handleNotSuffucientBalanceException(e:NotSufficientBalanceException): ResponseEntity<ResponseEmptyResult>{
        val responseError = ResponseEmptyResult(e.statusError,message.getMessageCode(e.warnings),e.timestamp)

        return ResponseEntity(responseError,HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(ClientInProcessException::class)
    fun handleClientInProcessException(e:ClientInProcessException): ResponseEntity<ResponseEmptyResult>{
        val responseError = ResponseEmptyResult(e.statusError,message.getMessageCode(e.warnings),e.timestamp)

        return ResponseEntity(responseError,HttpStatus.UNPROCESSABLE_ENTITY)
    }


    @ExceptionHandler(AccountAndClientDivergentException::class)
    fun handleAccountAndClientDivergentException(e: AccountAndClientDivergentException): ResponseEntity<ResponseEmptyResult>{
        val responseErrorExcep = ResponseEmptyResult(e.statusError,message.getMessageCode(e.warnings), e.timestamp)

        return ResponseEntity(responseErrorExcep,HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(AccountNotFoundException::class)
    fun handleAccountNotFoundException(e: AccountNotFoundException): ResponseEntity<ResponseEmptyResult>{
        val responseErrorExcep = ResponseEmptyResult(e.statusError,message.getMessageCode(e.warnings), e.timestamp)

        return ResponseEntity(responseErrorExcep,HttpStatus.NOT_FOUND)
    }


    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e:IllegalArgumentException): ResponseEntity<ResponseEmptyResult>{
        val responseErrorExcep = ResponseEmptyResult(
            HttpStatus.BAD_REQUEST.value(),
            message.getMessageCode(AllCodeErrors.ILLEGALARGUMENT.code),
            Date())
        return ResponseEntity(responseErrorExcep,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TransferToSameAccException::class)
    fun handleTransferToSameAccException(e: TransferToSameAccException): ResponseEntity<ResponseEmptyResult>{
        val responseErrorExcep = ResponseEmptyResult(e.statusError,
            message.getMessageCode(e.warnings),e.timestamp)

        return ResponseEntity(responseErrorExcep,HttpStatus.UNPROCESSABLE_ENTITY)
    }
}


