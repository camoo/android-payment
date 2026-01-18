package cm.camoo.payment

import cm.camoo.payment.http.CamooPaymentApi
import cm.camoo.payment.model.AccountResponse
import cm.camoo.payment.model.CashoutRequest
import cm.camoo.payment.model.Payment

class CamooPayManager(
    private val api: CamooPaymentApi
) {
    suspend fun cashout(request: CashoutRequest): Payment = api.cashout(request).cashOut
    suspend fun verify(paymentId: String): Payment = api.verify(paymentId).verify
    suspend fun account(): AccountResponse = api.account()
}
