package com.vehiforge.configurationService.core.vehicleConfig.repositories

import com.vehiforge.configurationService.core.vehicleConfig.models.ColorMaster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ColorMasterRepository: JpaRepository<ColorMaster, String> {
}