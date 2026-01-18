package cm.camoo.payment.callbacks

import java.net.URLEncoder
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object CamooSignatureVerifier {

    /**
     * Verify HMAC-SHA256 signature from Camoo GET notifications.
     *
     * Rules:
     * - Remove `sig`
     * - Sort params by key
     * - RFC3986 encoding
     * - HMAC-SHA256(secret=apiSecret)
     * - Compare hex digest (case-insensitive)
     */
    fun verify(params: Map<String, String>, apiSecret: String): Boolean {
        val sig = params["sig"] ?: return false

        val sorted = params
            .filterKeys { it != "sig" }
            .toSortedMap()

        val base = sorted.entries.joinToString("&") { (k, v) ->
            val enc = rfc3986(v)
            "$k=$enc"
        }

        val expected = hmacSha256Hex(base, apiSecret)
        return expected.equals(sig, ignoreCase = true)
    }

    private fun hmacSha256Hex(payload: String, secret: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "HmacSHA256"))
        val raw = mac.doFinal(payload.toByteArray(Charsets.UTF_8))
        return raw.joinToString("") { "%02x".format(it) }
    }

    private fun rfc3986(input: String): String =
        URLEncoder.encode(input, "UTF-8")
            .replace("+", "%20")
            .replace("%7E", "~")
}
