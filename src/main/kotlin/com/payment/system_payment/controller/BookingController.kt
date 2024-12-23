package com.payment.system_payment.controller

import com.payment.system_payment.domain.model.Booking
import com.payment.system_payment.service.BookingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/booking")
class BookingController(private val bookingService: BookingService) {

    @PostMapping
    fun book(): String {
        return bookingService.book()
    }

    @GetMapping("/status/{token}")
    fun getBookingStatus(@PathVariable token: String): String {
        return bookingService.getBookingStatus(token)
    }

    @GetMapping("/all")
    fun getAllBookings(): List<Booking> {
        return bookingService.getAllBookings()
    }

    @PutMapping("/cancellation/{token}")
    fun cancelBooking(@PathVariable token: String): ResponseEntity<String> {
        bookingService.cancelBooking(token)
        return ResponseEntity.status(HttpStatus.OK).body("Booking successfully canceled")
    }
}
