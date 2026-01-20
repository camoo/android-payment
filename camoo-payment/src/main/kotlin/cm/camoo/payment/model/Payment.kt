package cm.camoo.payment.model

import com.squareup.moshi.Json

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
