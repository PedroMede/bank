package com.zup.bank.unit.controllerTest

class TransferControllerTest {

//    private val transferController : TransferController = TransferController(
//        Mockito.mock(ServiceTransfer:: class.java),
//        Mockito.mock(KafkaTemplate<String,Transfer>::class.java)
//    )
//
//    lateinit var transferDTO : TransferDTO
//    lateinit var accountOrigin: Account
//    lateinit var accountDestiny: Account
//    lateinit var client: Client
//    lateinit var transfer: Transfer
//
//    @Test
//    fun createTransfer(){
//        client = Client(1,"Pedro","pedro@gmail.com","42511229846")
//        accountDestiny = Account(2,"0001","19", client,100.00,true)
//        accountOrigin = Account(1,"0001","18", client,100.00,true)
//        transferDTO = TransferDTO("18","19",20.00)
//        transfer = Transfer(1,accountOrigin,accountDestiny,20.00,null)
//
//        Mockito.`when`(transferController.transferServ.transfer(transferDTO)).thenReturn(transfer)
//
//        transferController.transfer(transferDTO)
//
//        Assert.assertEquals(HttpStatus.OK.value(), HttpStatus.OK.value())
//        Mockito.verify(transferController.transferServ,Mockito.times(1)).transfer(transferDTO)
//
//    }
//


}