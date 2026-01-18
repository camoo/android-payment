package cm.camoo.payment.model

data class AccountResponse(
    val code: Int,
    val message: String,
    val account: Account
) {
    data class Account(
        val balance: Double,
        val currency: String,
        val date: String
    )
}
