package com.payment.system_payment.scheduling

import com.payment.system_payment.service.BookingService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class ScheduleExecutor(private val bookingService: BookingService) {

    @Scheduled(fixedRate = 5 * 60 * 1000)
    fun bookingExpiryTime() {
        bookingService.bookingExpiryTime()
    }
}
