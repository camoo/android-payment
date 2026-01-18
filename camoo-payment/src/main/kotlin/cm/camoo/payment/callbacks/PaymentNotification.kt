package cm.camoo.payment.callbacks

data class PaymentNotification(
    val payment_id: String,
    val status: String,
    val status_time: String,
    val trx: String? = null,
    val sig: String
) {
    fun normalizedStatus(): String = status.uppercase()
}
