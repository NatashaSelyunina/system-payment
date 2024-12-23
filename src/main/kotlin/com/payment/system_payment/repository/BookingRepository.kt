package com.payment.system_payment.repository

import com.payment.system_payment.domain.model.Booking
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface BookingRepository : MongoRepository<Booking, Int> {

    fun findByToken(token: String): Booking?

    fun findById(id: ObjectId): Booking?
}
