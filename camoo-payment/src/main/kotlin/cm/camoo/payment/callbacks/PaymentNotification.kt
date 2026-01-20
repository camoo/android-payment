package cm.camoo.payment.callbacks

/**
 * Webhook signature verification.
 *
 * ⚠️ NOT USED on Android.
 * Intended for backend / JVM usage only.
 */
data class PaymentNotification(
    val payment_id: String,
    val status: String,
    val status_time: String,
    val trx: String? = null,
    val sig: String
) {
    fun normalizedStatus(): String = status.uppercase()
}
