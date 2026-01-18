package cm.camoo.payment.model

data class VerifyResponse(
    val code: Int,
    val message: String,
    val verify: Payment
)
