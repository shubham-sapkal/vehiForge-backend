package com.vehiforge.bookingService.core.booking.dto

class BookingRequestDto {

    class createBookingRequestBody(
        val bookingUserId: String,
        val vehicleConfigId: String,
        val color: String,
        val bookingPrice: Double,
        val amountPaid: Double,
        val bookingDate: String,
    )

}