package com.vehiforge.configurationService.core.vehicleConfig.repositories

import com.vehiforge.configurationService.core.vehicleConfig.models.VehicleConfiguration
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleConfigurationRepository: JpaRepository<VehicleConfiguration, String> {

    /*
    * DESCRIPTION:
    * */
    fun findByPlantIdIn(plantId: List<String>): List<VehicleConfiguration>

}