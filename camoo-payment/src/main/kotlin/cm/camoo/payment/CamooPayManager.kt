package cm.camoo.payment

import cm.camoo.payment.http.CamooPaymentApi
import cm.camoo.payment.model.AccountResponse
import cm.camoo.payment.model.CashoutRequest
import cm.camoo.payment.model.Payment

/*
 * Copyright 2025 Camoo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND.
 */
internal class CamooPayManager(
    private val api: CamooPaymentApi
) {

    suspend fun cashout(request: CashoutRequest): Payment {
        val response = api.cashout(request)
        return response.cashOut
    }

    suspend fun verify(paymentId: String): Payment {
        val response = api.verify(paymentId)
        return response.verify
    }

    suspend fun account(): AccountResponse {
        return api.account()
    }
}

