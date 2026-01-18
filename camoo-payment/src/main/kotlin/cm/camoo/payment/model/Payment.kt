package cm.camoo.payment.model

data class Payment(
    val id: String,
    val status: String,
    val amount: Double? = null,
    val currency: String? = null,
    val network: String? = null,
    val created_at: Long? = null,
    val completed_at: String? = null,
    val notified_at: String? = null,
    val external_reference: String? = null
)
