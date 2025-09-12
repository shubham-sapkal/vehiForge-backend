package com.vehiforge.bookingService.core.booking.controllers

import com.vehiforge.bookingService.core.booking.dto.BookingRequestDto
import com.vehiforge.bookingService.core.booking.models.BookedVehicles
import com.vehiforge.bookingService.core.booking.services.BookingVehiclesService
import com.vehiforge.bookingService.helpers.common_dto.GenerateJsonResponse
import com.vehiforge.bookingService.helpers.exceptions.CustomException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/booking")
class BookingController(
    val bookingVehiclesService: BookingVehiclesService
) {

    /*
    * Description:
    * accessible to:
    * */
    @PostMapping("/create")
    fun create(@RequestBody bookingVehicle: BookingRequestDto.createBookingRequestBody): GenerateJsonResponse<String> {

        try {
            return GenerateJsonResponse(
                200,
                bookingVehiclesService.createBooking(bookingVehicle),
                "",
                null
            )
        }
        catch(ce: CustomException) {
            println("CustomException ${ce.stackTrace}")
            return GenerateJsonResponse(
                500,
                "Something Went Wrong",
                ce.message ?: "Internal Server Error",
                null
            )
        }
        catch (e: Exception) {
            print("Exception $e")
            return GenerateJsonResponse(
                500,
                "Internal Server Error",
                e.message ?: "Internal Server Error",
                null
            )
        }


    }

    /*
    * Description:
    * accessible to:
    * */
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN_READ_WRITE', 'ADMIN_READ_ONLY')")
    fun getAllBooking(): GenerateJsonResponse<List<BookedVehicles>> {
        try {
            return GenerateJsonResponse(
                200,
                "Bookings Retrived Successfully!",
                "",
                bookingVehiclesService.getAllBookings()
            )
        }
        catch(ce: CustomException) {
            println("CustomException ${ce.stackTrace}")
            return GenerateJsonResponse(
                500,
                "Something Went Wrong",
                ce.message ?: "Internal Server Error",
                null
            )
        }
        catch (e: Exception) {
            print("Exception $e")
            return GenerateJsonResponse(
                500,
                "Internal Server Error",
                e.message ?: "Internal Server Error",
                null
            )
        }
    }

    /*
    * Description:
    * accessible to:
    * */
    @GetMapping("/booking-details/{bookingId}")
    fun getBookingDetails(@PathVariable bookingId: UUID): GenerateJsonResponse<BookedVehicles> {
        try {
            println("GET --> $bookingId ")
            return GenerateJsonResponse(
                200,
                "Bookings Retrived Successfully!",
                "",
                bookingVehiclesService.getBookingById(bookingId),
            )
        }
        catch(ce: CustomException) {
            println("CustomException ${ce.stackTrace}")
            return GenerateJsonResponse(
                500,
                "Something Went Wrong",
                ce.message ?: "Internal Server Error",
                null
            )
        }
        catch (e: Exception) {
            print("Exception $e")
            return GenerateJsonResponse(
                500,
                "Internal Server Error",
                e.message ?: "Internal Server Error",
                null
            )
        }
    }

}