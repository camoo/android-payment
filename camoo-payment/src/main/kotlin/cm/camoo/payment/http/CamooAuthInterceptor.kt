package cm.camoo.payment.http

import okhttp3.Interceptor
import okhttp3.Response

class CamooAuthInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-Api-Key", apiKey)
            .addHeader("X-Api-Secret", apiSecret)
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(request)
    }
}
