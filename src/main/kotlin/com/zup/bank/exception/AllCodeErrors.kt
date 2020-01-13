package com.zup.bank.exception

class AllCodeErrors(val code: String) {

    companion object {
        //Account
        val CODEACCOUNTREGISTERED: AllCodeErrors = AllCodeErrors("account.already.registered")
        val CODEACCOUNTNOTFOUND: AllCodeErrors = AllCodeErrors("account.not.found")
        val CODEBALANCENOTSUFF: AllCodeErrors = AllCodeErrors("account.balance.not.sufficient")
        val CODEACCANDCLIENTDIVERENT: AllCodeErrors = AllCodeErrors("account.divergent")
        val CODETRANFERSAMEACC: AllCodeErrors= AllCodeErrors("account.transfer.same")
        //Client
        val CODECLIENTNOTREG: AllCodeErrors = AllCodeErrors("client.not.register")
        val CODECLIENTREGISTERED: AllCodeErrors = AllCodeErrors("client.registered")
    }


}