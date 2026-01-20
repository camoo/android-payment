package cm.camoo.payment.http

import cm.camoo.payment.model.AccountResponse
import cm.camoo.payment.model.CashoutRequest
import cm.camoo.payment.model.CashoutResponse
import cm.camoo.payment.model.VerifyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
interface CamooPaymentApi {

    @POST("cashout")
    suspend fun cashout(@Body request: CashoutRequest): CashoutResponse

    @GET("verify")
    suspend fun verify(@Query("id") paymentId: String): VerifyResponse

    @GET("account")
    suspend fun account(): AccountResponse
}
