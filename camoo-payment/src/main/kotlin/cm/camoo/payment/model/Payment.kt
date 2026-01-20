package cm.camoo.payment.model

import com.squareup.moshi.Json

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

data class Payment(
    val id: String,
    val status: String,

    val amount: Double? = null,
    val currency: String? = null,
    val network: String? = null,
    val fees: Double? = null,

    @field:Json(name = "created_at")
    val createdAt: Long? = null,

    @field:Json(name = "completed_at")
    val completedAt: String? = null,

    @field:Json(name = "external_reference")
    val externalReference: String? = null,

    @field:Json(name = "net_amount")
    val netAmount: Double? = null,

    @field:Json(name = "phone_number")
    val phoneNumber: String? = null
)
