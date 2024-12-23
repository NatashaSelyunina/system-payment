package com.payment.system_payment.domain.model

import com.payment.system_payment.domain.enum.BookingStatus
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Booking(token: String) {

    @Id
    val id: ObjectId = ObjectId()
    val token: String? = null
    var status: BookingStatus? = null
}
