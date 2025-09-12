package com.vehiforge.bookingService.core.booking.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "booked_vehicles")
class BookedVehicles (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "booking_id")
    var bookingId: UUID? = null,

    @Column(name = "booking_user_id", nullable = false)
    var bookingUserId: String,

    @Column(name = "vehicle_config_id", nullable = false)
    var vehicleConfigId: String,

    @Column(name = "color", nullable = false)
    var color: String,

    @Column(name = "booking_price", nullable = false)
    var bookingPrice: Double,

    @Column(name = "amount_paid", nullable = false)
    var amountPaid: Double,

    @Column(name = "booking_date", nullable = false)
    var bookingDate: Date,

    @Column(name = "lock_deletion")
    var lockDeletion: Boolean = false,

    @Column(name = "booking_deletion_at", nullable = true)
    var bookingDeletedAt: Date? = null,

    @Column(name = "aspected_delivery_date", nullable = true)
    var aspectedDeliveryDate: Date? = null,

    @Column(name = "actual_delivery_date", nullable = true)
    var actualDeliveryDate: Date? = null,

    @Column(name = "delivery_confirmation")
    var deliveryConfirmation: Boolean? = false,

    @Column(name = "system_created_at")
    var systemCreatedAt: Date = Date()
)