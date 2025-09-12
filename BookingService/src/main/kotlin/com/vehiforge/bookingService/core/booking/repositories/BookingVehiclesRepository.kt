package com.vehiforge.bookingService.core.booking.repositories

import com.vehiforge.bookingService.core.booking.models.BookedVehicles
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BookingVehiclesRepository: JpaRepository<BookedVehicles, UUID> {
}