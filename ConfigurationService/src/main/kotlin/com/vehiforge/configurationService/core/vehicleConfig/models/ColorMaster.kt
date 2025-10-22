package com.vehiforge.configurationService.core.vehicleConfig.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class ColorMaster (

    @Id
    val colorName: String,

    @Column
    val colorDescription: String

)