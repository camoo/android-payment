package cm.camoo.payment.model

data class Payment(
    val id: String,
    val status: String,
    val amount: Double? = null,
    val currency: String? = null,
    val network: String? = null,
    val createdAt: Long? = null,
    val completedAt: String? = null,
    val notifiedAt: String? = null,
    val externalReference: String? = null,
    val fees : Double? = null,
    val netAmount : Double? = null,
    val phoneNumber: String? = null,
)
