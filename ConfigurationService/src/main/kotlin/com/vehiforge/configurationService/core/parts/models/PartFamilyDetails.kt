package com.vehiforge.configurationService.core.parts.models

import com.vehiforge.configurationService.helpers.constants.VehicleType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="part_family_details")
class PartFamilyDetails (

    @Id
    @Column(nullable = false, unique = true)
    val partFamilyName: String,

    @Column(nullable = true)
    var partFamilyDescription: String? = null,

    @Column(nullable = false)
    var applicableVehicleType: List<String>

)