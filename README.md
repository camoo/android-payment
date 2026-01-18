# Camoo Payment Android SDK

````markdown
# Camoo Payment Android SDK

Android (Kotlin) SDK for Camoo Payment API.

## Install (local module)
Include `:camoo-payment` as a module, or publish via JitPack/Maven later.

## Usage

```kotlin
val camoo = CamooPay.create(
  CamooConfig(
    apiKey = "YOUR_API_KEY",
    apiSecret = "YOUR_API_SECRET"
  )
)

val payment = camoo.cashout(
  CashoutRequest(
    amount = 5000.0,
    phone_number = "+237612345678",
    notification_url = "https://merchant.com/callback",
    external_reference = "order-123"
  )
)

val verified = camoo.verify(payment.id)
````

## Verify callback signature

```kotlin
val ok = CamooSignatureVerifier.verify(queryParams, apiSecret)
```
