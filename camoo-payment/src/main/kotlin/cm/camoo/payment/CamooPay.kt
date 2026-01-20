package cm.camoo.payment

import cm.camoo.payment.http.CamooClientFactory
import cm.camoo.payment.model.AccountResponse
import cm.camoo.payment.model.CashoutRequest
import cm.camoo.payment.model.Payment
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * SDK entry point.
 *
 * Must be initialized once before usage.
 */
object CamooPay {

    private lateinit var manager: CamooPayManager

    private val TERMINAL_STATUSES = setOf(
        "SUCCESS",
        "CONFIRMED",
        "FAILED",
        "ERRORED",
        "CANCELLED"
    )

    @Synchronized
    fun init(apiKey: String, apiSecret: String) {
        if (::manager.isInitialized) return

        require(apiKey.isNotBlank()) { "apiKey is required" }
        require(apiSecret.isNotBlank()) { "apiSecret is required" }

        val config = CamooConfig(
            apiKey = apiKey,
            apiSecret = apiSecret,
            baseUrl = CamooEndpoints.BASE_URL
        )

        val api = CamooClientFactory.create(config)
        manager = CamooPayManager(api)
    }

    private fun checkInitialized() {
        check(::manager.isInitialized) {
            "CamooPay is not initialized. Call CamooPay.init(apiKey, apiSecret) first."
        }
    }


    suspend fun cashout(
        request: CashoutRequest
    ): CamooResult<Payment> {
        checkInitialized()
        return runCatching {
            manager.cashout(request)
        }.fold(
            onSuccess = { CamooResult.Ok(it) },
            onFailure = { CamooResult.Err(it.message ?: "Cashout failed", it) }
        )
    }

    suspend fun verify(paymentId: String): CamooResult<Payment> {
        checkInitialized()
        return runCatching {
            manager.verify(paymentId)
        }.fold(
            onSuccess = { CamooResult.Ok(it) },
            onFailure = { CamooResult.Err(it.message ?: "Verification failed", it) }
        )
    }

    suspend fun account(): CamooResult<AccountResponse> {
        checkInitialized()
        return runCatching {
            manager.account()
        }.fold(
            onSuccess = { CamooResult.Ok(it) },
            onFailure = { CamooResult.Err(it.message ?: "Account fetch failed", it) }
        )
    }



    /**
     * Polls payment status until it reaches a terminal state.
     *
     * Terminal states (case-insensitive):
     * - SUCCESS
     * - CONFIRMED
     * - FAILED
     * - ERRORED
     * - CANCELLED
     */
    suspend fun waitForPayment(
        paymentId: String,
        pollIntervalMs: Long = 3_000L,
        timeoutMs: Long = 120_000L
    ): CamooResult<Payment> {
        checkInitialized()

        val startTime = System.currentTimeMillis()

        while (currentCoroutineContext().isActive) {

            // Timeout check
            if (System.currentTimeMillis() - startTime > timeoutMs) {
                return CamooResult.Err("Payment verification timed out")
            }

            // Verify payment
            when (val result = verify(paymentId)) {

                is CamooResult.Err -> {
                    return result // network / backend error
                }

                is CamooResult.Ok -> {
                    val payment = result.value
                    val status = payment.status.uppercase()

                    if (status in TERMINAL_STATUSES) {
                        return CamooResult.Ok(payment)
                    }
                }
            }

            delay(pollIntervalMs)
        }
        return CamooResult.Err(
            "Payment verification timed out after ${timeoutMs / 1000}s"
        )
    }

}

