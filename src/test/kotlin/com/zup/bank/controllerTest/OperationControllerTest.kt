package com.zup.bank.controllerTest


import com.zup.bank.controller.OperationsController

import com.zup.bank.service.ServiceOperations

import org.mockito.Mockito


class OperationControllerTest {

    private val operationController : OperationsController = OperationsController(
        Mockito.mock(ServiceOperations:: class.java)
    )


}