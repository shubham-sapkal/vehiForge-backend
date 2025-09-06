package com.vehiforge.bookingService.core.booking.controllers

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/booking")
class BookingController {

    /*
    * Description:
    * accessible to:
    * */
    @PostMapping("/create")
    fun create(): String {
        return "Booking Created"
    }

    /*
    * Description:
    * accessible to:
    * */
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN1_READ_WRITE', 'ADMIN_READ_ONLY')")
    fun getAllBooking(): String {
        return "All Booking"
    }

    /*
    * Description:
    * accessible to:
    * */
    @GetMapping("/{bookingId}")
    fun getBookingDetails(@PathVariable bookingId: String): String {
        return "Booking Details on Id $bookingId"
    }

}