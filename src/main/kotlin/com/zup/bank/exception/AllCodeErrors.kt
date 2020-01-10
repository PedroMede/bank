package com.zup.bank.exception

class AllCodeErrors(code: String) {

    companion object {
        //Account
        val CODEACCOUNTREGISTERED: AllCodeErrors = AllCodeErrors("account.already.registered")
        val CODEACCOUNTNOTFOUND: AllCodeErrors = AllCodeErrors("account.not.found")


        //Client
        val CODECLIENTNOTREG: AllCodeErrors = AllCodeErrors("client.not.register")

    }


}