package cm.camoo.payment.http

import okhttp3.Interceptor
import okhttp3.Response

internal class CamooAuthInterceptor(
    private val apiKey: String,
    private val apiSecret: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .header("X-Api-Key", apiKey)
            .header("X-Api-Secret", apiSecret)
            .build()

        return chain.proceed(request)
    }
}

