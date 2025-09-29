package com.vehiforge.configurationService.core.parts.repositories

import com.vehiforge.configurationService.core.parts.models.PartFamilyDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartFamilyDetailsRepository: JpaRepository<PartFamilyDetails, String> {
}