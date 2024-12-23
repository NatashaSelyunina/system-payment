package com.payment.system_payment.service

import com.payment.system_payment.domain.enum.BookingStatus
import com.payment.system_payment.domain.enum.PaymentStatus
import com.payment.system_payment.domain.model.Booking
import com.payment.system_payment.domain.model.Payment
import com.payment.system_payment.repository.BookingRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class BookingService(private val bookingRepository: BookingRepository, private val paymentService: PaymentService) {

    fun book(): String {
        val token = UUID.randomUUID().toString()
        val newBooking = Booking(token)
        newBooking.status = BookingStatus.NEW
        newBooking.createdAt = LocalDateTime.now()
        bookingRepository.save(newBooking)

        val newPayment = Payment()
        newPayment.status = PaymentStatus.PENDING
        newPayment.bookingId = newBooking.id
        paymentService.savePayment(newPayment)

        return token
    }

    fun getBookingStatus(token: String): String {
        val bookingFromDb = bookingRepository.findByToken(token)
        return bookingFromDb?.status?.toString() ?: throw RuntimeException("Booking with token $token not found")
    }

    fun getAllBookings(): List<Booking> {
        return bookingRepository.findAll()
    }

    fun cancelBooking(token: String) {
        val bookingFromDb = findBookingByToken(token) ?: throw RuntimeException("Booking with token $token not found")
        val paymentFromDB = paymentService.findPaymentByBookingId(bookingFromDb.id)

        when (bookingFromDb.status) {
            BookingStatus.NEW -> {
                bookingFromDb.status = BookingStatus.CANCELED
                bookingRepository.save(bookingFromDb)
                if (paymentFromDB != null) {
                    paymentService.deletePayment(paymentFromDB)
                }
            }
            BookingStatus.CANCELED -> {
                throw RuntimeException("Booking with token $token has already been cancelled")
            }
            else -> {
                throw RuntimeException("Booking with token $token has already been paid")
            }
        }
    }

    fun findBookingByToken(token: String): Booking? {
        return bookingRepository.findByToken(token)
    }

    fun saveBooking(booking: Booking) {
        bookingRepository.save(booking)
    }
}
