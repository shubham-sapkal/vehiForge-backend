package com.vehiforge.configurationService.core.vehicleConfig.repositories

import com.vehiforge.configurationService.core.vehicleConfig.models.PlantMaster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlantMasterRepository: JpaRepository<PlantMaster, Integer> {
}