package com.payment.system_payment.controller

import com.payment.system_payment.domain.PaymentRequest
import com.payment.system_payment.domain.model.Payment
import com.payment.system_payment.service.PaymentService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/payment")
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping
    fun pay(@RequestBody @Valid paymentRequest: PaymentRequest): String {
        return paymentService.pay(paymentRequest)
    }

    @GetMapping("/status/{token}")
    fun getPaymentStatus(@PathVariable token: String): String {
        return paymentService.getPaymentStatus(token)
    }

    @GetMapping("/all")
    fun getAllPayments(): List<Payment> {
        return paymentService.getAllPayments()
    }
}
