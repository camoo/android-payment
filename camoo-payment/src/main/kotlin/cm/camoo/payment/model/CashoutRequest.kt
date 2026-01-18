package cm.camoo.payment.model

data class CashoutRequest(
    val amount: Double,
    val phone_number: String,
    val notification_url: String? = null,
    val currency: String = "XAF",
    val external_reference: String? = null,
    val shopping_cart_details: Map<String, Any>? = null
)
