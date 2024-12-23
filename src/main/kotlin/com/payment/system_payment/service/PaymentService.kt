package com.payment.system_payment.service

import com.payment.system_payment.domain.PaymentRequest
import com.payment.system_payment.domain.enum.BookingStatus
import com.payment.system_payment.domain.enum.PaymentStatus
import com.payment.system_payment.domain.enum.Provider
import com.payment.system_payment.domain.model.Payment
import com.payment.system_payment.repository.PaymentRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class PaymentService(private val paymentRepository: PaymentRepository,
                     private val bookingService: BookingService) {

    fun pay(paymentRequest: PaymentRequest): Payment {
        val bookingFromDB = bookingService.findBookingByToken(paymentRequest.token)
            ?: throw RuntimeException("Booking not found")
        val paymentFromDB = findPaymentByBookingId(bookingFromDB.id)

        if (bookingFromDB.status == BookingStatus.PAID) {
            throw RuntimeException("This booking has already been paid!")
        } else {
            paymentFromDB.status = PaymentStatus.PENDING

            if (paymentRequest.provider == Provider.RUSSIA) {
                payRussianProvider(paymentFromDB)
            }
            if (paymentRequest.provider == Provider.OTHER) {
                payOtherProvider(paymentFromDB)
            }

            paymentFromDB.bookingId = bookingFromDB.id
            paymentRepository.save(paymentFromDB)
        }

        return paymentFromDB
    }

    fun payRussianProvider(payment: Payment): Payment {
        val randomValues = Random.nextInt(0, 101)
        if (randomValues < 50) {
            payment.status = PaymentStatus.SUCCESS
        } else {
            payment.status = PaymentStatus.FAILED
        }

        return payment
    }

    fun payOtherProvider(payment: Payment): Payment {
        try {
            Thread.sleep(10000)
        } catch (exception: InterruptedException) {
            Thread.currentThread().interrupt()
        }

        val randomValues = Random.nextInt(0, 101)
        if (randomValues < 50) {
            payment.status = PaymentStatus.SUCCESS
        } else {
            payment.status = PaymentStatus.FAILED
        }

        return payment
    }

    fun getPaymentStatus(token: String): String {
        val bookingFromDB = bookingService.findBookingByToken(token)
            ?: throw RuntimeException("Booking with token $token not found")
        val paymentFromDB = findPaymentByBookingId(bookingFromDB.id)

        return paymentFromDB?.status?.toString() ?: throw RuntimeException("Payment by token $token not found")
    }

    fun getAllPayments(): List<Payment> {
        return paymentRepository.findAll()
    }

    fun savePayment(payment: Payment) {
        paymentRepository.save(payment)
    }
    
    fun deletePayment(payment: Payment) {
        paymentRepository.delete(payment)
    }
    
    fun findPaymentByBookingId(bookingId: ObjectId): Payment? {
        return paymentRepository.findByBookingId(bookingId)
    }
}
