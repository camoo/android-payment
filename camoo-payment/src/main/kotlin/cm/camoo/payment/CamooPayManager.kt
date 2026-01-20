package cm.camoo.payment

import cm.camoo.payment.http.CamooPaymentApi
import cm.camoo.payment.model.AccountResponse
import cm.camoo.payment.model.CashoutRequest
import cm.camoo.payment.model.Payment

internal class CamooPayManager(
    private val api: CamooPaymentApi
) {

    suspend fun cashout(request: CashoutRequest): Payment {
        val response = api.cashout(request)
        return response.cashOut
    }

    suspend fun verify(paymentId: String): Payment {
        val response = api.verify(paymentId)
        return response.verify
    }

    suspend fun account(): AccountResponse {
        return api.account()
    }
}

