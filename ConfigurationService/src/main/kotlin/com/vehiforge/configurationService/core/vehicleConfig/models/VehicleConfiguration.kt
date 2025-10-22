package com.vehiforge.configurationService.core.vehicleConfig.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.vehiforge.configurationService.autitorAware.entities.AuditableEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name="vehicle_configuration")
class VehicleConfiguration (

    @Id
    val configurationId: String,

    @Column(name="plant_id")
    var plantId: Integer,

    @Column(name = "configuration_title")
    var configurationTitle: String,

    @Column
    var description: String,

    @Column
    var grossWeight: String,

    @Column
    var steeringType: String,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "vehicle_config_colors",
        joinColumns = [JoinColumn(name = "configuration_id")],
        inverseJoinColumns = [JoinColumn(name = "color_name")]
    )
    var applicableColors: MutableList<ColorMaster> = mutableListOf(),

    @Column
    var noOfTyres: Integer,

    @Column
    var tyreSize: Float,

    @OneToMany(mappedBy = "vehicleConfiguration", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    var configuredParts: MutableList<ConfiguredParts>? = mutableListOf()

) : AuditableEntity()