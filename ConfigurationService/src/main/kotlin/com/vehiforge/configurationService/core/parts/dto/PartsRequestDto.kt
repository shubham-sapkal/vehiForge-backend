package com.vehiforge.configurationService.core.parts.dto

class PartsRequestDto {

    class CreateOrUpdatePartFamily(
        val partFamilyName: String,
        val partFamilyDescription: String?,
        val applicableVehicleType: List<String>
    )

    class DeletePartFamily(
        val partFamilyName: String
    )

    class CreateOrUpdatePartsMaster(
        val plantId: Integer,
        val partId: String,
        val partName: String,
        val partDescription: String,
        val partFamilyName: String,
        val standardCost: Double,
    )

}