package com.vehiforge.configurationService.core.parts.models

import com.vehiforge.configurationService.autitorAware.entities.AuditableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "parts_master")
class PartsMaster (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false)
    var plantId: Integer,

    @Column(nullable = false)
    var partId: String,

    @Column(nullable = false)
    var partName: String,

    @Column(nullable = true)
    var partDescription: String,

    @OneToOne(fetch = FetchType.EAGER)
    var partFamily: PartFamilyDetails,

    @Column(nullable = false)
    var standardCost: Double,

    @Column(nullable = false)
    var lastCostUpdateAt: Date,

    @Column
    var isDeleted: Boolean? = false,

    @Column(nullable = true)
    var deletedBy: String? = null
) : AuditableEntity()