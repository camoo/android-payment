package cm.camoo.payment

sealed class CamooResult<out T> {
    data class Ok<T>(val value: T) : CamooResult<T>()
    data class Err(val message: String, val cause: Throwable? = null) : CamooResult<Nothing>()
}
