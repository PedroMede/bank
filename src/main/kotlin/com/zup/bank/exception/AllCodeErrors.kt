package com.zup.bank.exception

class AllCodeErrors(code: String) {

    companion object {
        //Account
        val CODEACCOUNTREGISTERED: AllCodeErrors = AllCodeErrors("account.already.registered")
        val CODEACCOUNTNOTFOUND: AllCodeErrors = AllCodeErrors("account.not.found")
        val CODEBALANCENOTSUFF: AllCodeErrors = AllCodeErrors("account.balance.not.sufficient")
        val CODEACCANDCLIENTDIVERENT: AllCodeErrors = AllCodeErrors("account.divergent")
        //Client
        val CODECLIENTNOTREG: AllCodeErrors = AllCodeErrors("client.not.register")
        val CODECLIENTREGISTERED: AllCodeErrors = AllCodeErrors("client.registered")
    }


}