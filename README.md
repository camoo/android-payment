# 📦 Camoo Payment Android SDK

## Installation

```kotlin
dependencies {
    implementation("cm.camoo:camoo-payment-android:0.1.0")
}
```

---

## Usage

### 1️⃣ Initialize the SDK

Initialize **once**, ideally in your `Application` class.

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        CamooPay.init(
            apiKey = "pk_live_xxx",
            apiSecret = "sk_mobile_xxx"
        )
    }
}
```

> ⚠️ **Important**
> `CamooPay.init()` must be called before any other SDK method.

---

### 2️⃣ Create a cashout payment

All SDK operations are **suspending functions** and must be called from a coroutine.

By default, `waitForPayment()` polls for up to **2 minutes**.
You can customize both the polling interval and timeout.


```kotlin
lifecycleScope.launch {
    val cashoutResult = CamooPay.cashout(
        CashoutRequest(
            amount = 5000.0,
            phone_number = "+237612345678",
            external_reference = "order-123"
        )
    )

    if (cashoutResult is CamooResult.Ok) {
        val paymentId = cashoutResult.value.id

        val finalResult = CamooPay.waitForPayment(paymentId)

        when (finalResult) {
            is CamooResult.Ok -> {
                Log.d("CamooPay", "Final status: ${finalResult.value.status}")
            }
            is CamooResult.Err -> {
                Log.e("CamooPay", finalResult.message)
            }
        }
    }
}
```

---

### 3️⃣ Verify a payment

Use the payment ID returned during cashout.

```kotlin
lifecycleScope.launch {
    val result = CamooPay.verify(paymentId = "payment-id-123")

    when (result) {
        is CamooResult.Ok -> {
            val payment = result.value
            Log.d("CamooPay", "Payment status: ${payment.status}")
        }

        is CamooResult.Err -> {
            Log.e("CamooPay", "Verification failed: ${result.message}")
        }
    }
}
```

---

### 4️⃣ Retrieve account information

```kotlin
lifecycleScope.launch {
    val result = CamooPay.account()

    when (result) {
        is CamooResult.Ok -> {
            val account = result.value
            Log.d("CamooPay", "Account balance: ${account.balance}")
        }

        is CamooResult.Err -> {
            Log.e("CamooPay", "Account fetch failed: ${result.message}")
        }
    }
}
```

---

## Error handling with `CamooResult`

The SDK **never throws exceptions** to consumers.

Instead, all operations return:

```kotlin
sealed class CamooResult<out T> {
    data class Ok<T>(val value: T) : CamooResult<T>()
    data class Err(val message: String, val cause: Throwable? = null) : CamooResult<Nothing>()
}
```

### Why this matters

* No `try/catch` required
* Predictable control flow
* Safer production behavior
* Easier UI error handling

---

## Coroutine requirement

All SDK methods are `suspend`.

You can call them from:

* `lifecycleScope`
* `viewModelScope`
* Any custom coroutine scope

Example:

```kotlin
viewModelScope.launch {
    CamooPay.cashout(...)
}
```

---

## Security notice

> ⚠️ **Security notice**
> This SDK requires an API secret for mobile usage.
> The secret **must be mobile-scoped**, rate-limited, and must not be reused on backend systems.
