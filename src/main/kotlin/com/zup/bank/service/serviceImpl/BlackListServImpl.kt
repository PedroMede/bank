package com.zup.bank.service.serviceImpl

import com.zup.bank.common.AllCodeErrors
import com.zup.bank.dto.BlacklistDTO
import com.zup.bank.exception.customErrors.ExceptionClientAlreadyReg
import com.zup.bank.model.Blacklist
import com.zup.bank.repository.BlacklistRepository
import com.zup.bank.service.ServiceBlacklist
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BlackListServImpl(
    private val serviceBlack: BlacklistRepository
): ServiceBlacklist {

    override fun createBlack(blacklist: Blacklist): Blacklist {
        verifyClient(blacklist.cpf!!)
        return serviceBlack.save(blacklist)
    }

    override fun getAll(): MutableList<Blacklist> {
        return serviceBlack.findAll()
    }

    override fun deleteByCpf(cpf: String) {
        serviceBlack.deleteByCpf(cpf)
    }

    override fun deleteAll() {
        serviceBlack.deleteAll()
    }

    fun verifyClient(cpf:String){
        if(serviceBlack.existsByCpf(cpf)){
            throw ExceptionClientAlreadyReg(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                AllCodeErrors.CODEBLACKLISTCLIENT.code, "cpf"
            )
        }
    }
}