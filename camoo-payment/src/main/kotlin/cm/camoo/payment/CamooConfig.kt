package cm.camoo.payment

data class CamooConfig(
    val apiKey: String,
    val apiSecret: String,
    val baseUrl: String = "https://api.camoo.cm/v1/payment/"
)
