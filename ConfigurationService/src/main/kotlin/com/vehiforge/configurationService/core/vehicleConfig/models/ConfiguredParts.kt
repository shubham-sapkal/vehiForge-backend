package com.vehiforge.configurationService.core.vehicleConfig.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vehiforge.configurationService.autitorAware.entities.AuditableEntity
import com.vehiforge.configurationService.core.parts.models.PartsMaster
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name="configured_parts")
class ConfiguredParts(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuration_id", nullable = false)
    @JsonIgnore
    val vehicleConfiguration: VehicleConfiguration,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "part_id", nullable = false)
    val mappedPart: PartsMaster,

    @Column(name = "quantity")
    val quantity: Integer

): AuditableEntity() {

}