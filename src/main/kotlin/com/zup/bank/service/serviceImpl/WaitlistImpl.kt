package com.zup.bank.service.serviceImpl

import com.zup.bank.enum.ClientStatus
import com.zup.bank.model.Waitlist
import com.zup.bank.repository.WaitListRepository
import com.zup.bank.service.ServiceWaitlist
import org.springframework.stereotype.Service

@Service
class WaitlistImpl(val service: WaitListRepository): ServiceWaitlist {
    override fun createBlocked(blocked: Waitlist) {
        service.save(blocked)
    }

    override fun getAll(): MutableList<Waitlist> {
       return service.findAll()
    }

    override fun getByCpf(cpf: String,status: ClientStatus): Waitlist {
        return service.findByCpfAndStatus(cpf,status)
    }

    override fun deleteByCpf(cpf: String) {
        service.deleteByCpf(cpf)
    }

    override fun findByCpf(cpf: String): Waitlist {
       return service.findByCpf(cpf)
    }
}