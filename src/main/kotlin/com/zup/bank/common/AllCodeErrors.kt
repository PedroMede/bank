package com.zup.bank.common

class AllCodeErrors(val code: String) {

    companion object {
        //Account
        val CODEACCOUNTREGISTERED: AllCodeErrors = AllCodeErrors("account.already.registered")
        val CODEACCOUNTNOTFOUND: AllCodeErrors = AllCodeErrors("account.not.found")
        val CODEBALANCENOTSUFF: AllCodeErrors = AllCodeErrors("account.balance.not.sufficient")
        val CODEACCANDCLIENTDIVERENT: AllCodeErrors = AllCodeErrors("account.divergent")
        val CODETRANFERSAMEACC: AllCodeErrors = AllCodeErrors("account.transfer.same")
        val CODENUMACC: AllCodeErrors =AllCodeErrors("origin.destiny.account")
        val CODEORIGIN: AllCodeErrors = AllCodeErrors("account.origin")
        val CODEDESTINY: AllCodeErrors = AllCodeErrors("account.destiny")
        val CODEVALUE:  AllCodeErrors = AllCodeErrors("account.value")

        //Client
        val CODECLIENTNOTREG: AllCodeErrors = AllCodeErrors("client.not.register")
        val CODECLIENTREGISTERED: AllCodeErrors = AllCodeErrors("client.registered")
        val CODEBLACKLISTCLIENT : AllCodeErrors = AllCodeErrors("client.black")
        val CODECLIENTPROCESS: AllCodeErrors = AllCodeErrors("client.process")
        //common
        val CPFNOTFOUND: AllCodeErrors = AllCodeErrors("cpf.not.found")
        val ILLEGALARGUMENT: AllCodeErrors = AllCodeErrors("illegal.argument")

    }


}