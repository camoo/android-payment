package cm.camoo.payment

import cm.camoo.payment.http.CamooClientFactory
import cm.camoo.payment.model.AccountResponse
import cm.camoo.payment.model.CashoutRequest
import cm.camoo.payment.model.Payment

/**
 * Simple facade for SDK consumers.
 */
class CamooPay private constructor(
    private val manager: CamooPayManager
) {
    suspend fun cashout(request: CashoutRequest): Payment = manager.cashout(request)
    suspend fun verify(paymentId: String): Payment = manager.verify(paymentId)
    suspend fun account(): AccountResponse = manager.account()

    companion object {
        fun create(config: CamooConfig): CamooPay {
            val api = CamooClientFactory.create(config)
            return CamooPay(CamooPayManager(api))
        }
    }
}
