package com.vehiforge.configurationService.core.vehicleConfig.models

import com.vehiforge.configurationService.autitorAware.entities.AuditableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="plant_master")
class PlantMaster (

    @Id
    val plantId: Integer,

    @Column(nullable = false)
    var plantName: String,

    @Column
    var plantLocation: String,

) : AuditableEntity()