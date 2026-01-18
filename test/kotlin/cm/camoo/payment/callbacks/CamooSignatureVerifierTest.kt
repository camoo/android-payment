package cm.camoo.payment.callbacks

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CamooSignatureVerifierTest {

    @Test
    fun verify_returnsTrue_forValidSig() {
        val secret = "test_secret"

        val paramsWithoutSig = mapOf(
            "payment_id" to "934ca3f6",
            "status" to "confirmed",
            "status_time" to "2026-01-18T11:42:03+00:00",
            "trx" to "my-site-ref"
        )

        val sig = computeSigLikeSdk(paramsWithoutSig, secret)

        val params = paramsWithoutSig + ("sig" to sig)

        assertTrue(CamooSignatureVerifier.verify(params, secret))
    }

    @Test
    fun verify_returnsFalse_forInvalidSig() {
        val secret = "test_secret"

        val params = mapOf(
            "payment_id" to "934ca3f6",
            "status" to "confirmed",
            "status_time" to "2026-01-18T11:42:03+00:00",
            "trx" to "my-site-ref",
            "sig" to "deadbeef"
        )

        assertFalse(CamooSignatureVerifier.verify(params, secret))
    }

    private fun computeSigLikeSdk(params: Map<String, String>, secret: String): String {
        // Use the SDK itself to compute expected signature path:
        val withPlaceholder = params + ("sig" to "placeholder")
        // we can't call verify() directly to compute, so mimic internal logic:
        val sorted = withPlaceholder.filterKeys { it != "sig" }.toSortedMap()
        val base = sorted.entries.joinToString("&") { (k, v) ->
            val enc = java.net.URLEncoder.encode(v, "UTF-8")
                .replace("+", "%20")
                .replace("%7E", "~")
            "$k=$enc"
        }

        val mac = javax.crypto.Mac.getInstance("HmacSHA256")
        mac.init(javax.crypto.spec.SecretKeySpec(secret.toByteArray(), "HmacSHA256"))
        val raw = mac.doFinal(base.toByteArray())
        return raw.joinToString("") { "%02x".format(it) }
    }
}
