package com.vehiforge.bookingService.core.booking.services

import com.vehiforge.bookingService.core.booking.dto.BookingRequestDto
import com.vehiforge.bookingService.core.booking.models.BookedVehicles
import com.vehiforge.bookingService.core.booking.repositories.BookingVehiclesRepository
import com.vehiforge.bookingService.helpers.exceptions.CustomException
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Service
class BookingVehiclesService(
    var bookingVehiclesRepository: BookingVehiclesRepository
) {

    /*
    * Description: Function to book vehicle
    * accessible to: OPEN
    * */
    fun createBooking(bookingVehicles: BookingRequestDto.createBookingRequestBody): String {

        try {

            // Create Booking

            val bookingDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

            val bookingDetails = BookedVehicles(
                bookingUserId = bookingVehicles.bookingUserId,
                vehicleConfigId = bookingVehicles.vehicleConfigId,
                color = bookingVehicles.color,
                bookingPrice = bookingVehicles.bookingPrice,
                amountPaid = bookingVehicles.amountPaid,
                bookingDate = bookingDateFormat.parse(bookingVehicles.bookingDate)
            )

            bookingVehiclesRepository.save(bookingDetails);

            // TODO: Publish Booking details on kafka topic


            return "Booking Created Successfully!"

        }
        catch (e: Exception) {
            println("Booking Service ${e.message} ")
            throw CustomException(500, e.message ?: "Internal Server Error!")
        }


    }

    /*
    * Description: Get All Bookings
    * Accessible to: ADMIN
    * */
    fun getAllBookings(): List<BookedVehicles> {
        return bookingVehiclesRepository.findAll();
    }

    /*
    * Description: Get Booking By Id
    * accessible to:
    * */
    fun getBookingById(bookingId: UUID): BookedVehicles {

        val bookingDetails = bookingVehiclesRepository.findById(bookingId);

        println("--> $bookingDetails")

        if(bookingDetails.isEmpty) {
            throw CustomException(404, "Booking Details Not Found!")
        }

        return bookingDetails.get();

    }


}