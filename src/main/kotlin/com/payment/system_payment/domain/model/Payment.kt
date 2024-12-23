package com.payment.system_payment.domain.model

import com.payment.system_payment.domain.enum.PaymentStatus
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Payment() {

    var status: PaymentStatus? = null
    var bookingId: ObjectId? = null
}
