package com.zup.bank.service.serviceImpl

import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.BlockedClient
import com.zup.bank.repository.BlacklistBlocked
import com.zup.bank.service.ServiceBlackBlocked
import org.springframework.stereotype.Service

@Service
class BlackBlockedImpl(val service: BlacklistBlocked): ServiceBlackBlocked {
    override fun createBlocked(blocked: BlockedClient) {
        service.save(blocked)
    }

    override fun getAll(): MutableList<BlockedClient> {
       return service.findAll()
    }

    override fun getByCpf(cpf: String,status: ClientStatus): BlockedClient {
        return service.findByCpfAndStatus(cpf,status)
    }

    override fun deleteByCpf(cpf: String) {
        service.deleteByCpf(cpf)
    }
}