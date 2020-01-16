package com.zup.bank.common

import org.springframework.context.MessageSource
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct


@Component
class Messages (val messageSource: MessageSource) {

    private lateinit var messageSouceAcessor: MessageSourceAccessor

    @PostConstruct
    private fun init(){
       messageSouceAcessor = MessageSourceAccessor(messageSource, Locale.getDefault())
    }

    fun getMessageCode(code: String) :  String{
        return messageSouceAcessor.getMessage(code)
    }

}