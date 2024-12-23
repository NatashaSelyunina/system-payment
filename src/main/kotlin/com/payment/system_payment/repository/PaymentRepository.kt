package com.payment.system_payment.repository

import com.payment.system_payment.domain.model.Payment
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PaymentRepository : MongoRepository<Payment, Int> {

    fun findByBookingId(bookingId: ObjectId): Payment
}
