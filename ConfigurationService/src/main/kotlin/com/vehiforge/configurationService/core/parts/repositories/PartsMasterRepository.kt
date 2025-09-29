package com.vehiforge.configurationService.core.parts.repositories

import com.vehiforge.configurationService.core.parts.models.PartsMaster
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface PartsMasterRepository: JpaRepository<PartsMaster, UUID> {
    /*
    * Description: Function to filter Data Based on Plant id and Part id
    * */
    fun findByPlantIdAndPartId(plantId: Integer, partId: String): Optional<PartsMaster>

}