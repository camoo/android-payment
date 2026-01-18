package cm.camoo.payment.model

data class CashoutResponse(
    val code: Int,
    val message: String,
    val cashOut: Payment
)
