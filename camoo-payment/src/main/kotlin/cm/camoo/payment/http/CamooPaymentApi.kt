package cm.camoo.payment.http

import cm.camoo.payment.model.AccountResponse
import cm.camoo.payment.model.CashoutRequest
import cm.camoo.payment.model.CashoutResponse
import cm.camoo.payment.model.VerifyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CamooPaymentApi {

    @POST("cashout")
    suspend fun cashout(@Body request: CashoutRequest): CashoutResponse

    @GET("verify")
    suspend fun verify(@Query("id") paymentId: String): VerifyResponse

    @GET("account")
    suspend fun account(): AccountResponse
}
