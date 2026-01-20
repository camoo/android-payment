package cm.camoo.payment.model

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
data class CashoutRequest(
    val amount: Double,
    val phone_number: String,
    val currency: String = "XAF",
    val external_reference: String? = null,
    val shopping_cart_details: Map<String, Any>? = null
)
