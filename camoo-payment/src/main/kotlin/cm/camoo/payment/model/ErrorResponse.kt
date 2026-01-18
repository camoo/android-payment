package cm.camoo.payment.model

data class ErrorResponse(
    val code: Int? = null,
    val message: String? = null,
    val errors: Map<String, Any>? = null
)
