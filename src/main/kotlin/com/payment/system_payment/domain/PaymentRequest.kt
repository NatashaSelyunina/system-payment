package com.payment.system_payment.domain

import com.payment.system_payment.domain.enum.Provider
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PaymentRequest(

    @field:NotBlank(message = "Token cannot be blank")
    val token: String,

    @field:NotNull(message = "Country cannot be null")
    val provider: Provider,

    @field:NotBlank(message = "Card details cannot be blank")
    val cardDetails: String
)
