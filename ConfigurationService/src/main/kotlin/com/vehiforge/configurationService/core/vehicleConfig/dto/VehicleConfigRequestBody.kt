package com.vehiforge.configurationService.core.vehicleConfig.dto

class VehicleConfigRequestBody {

    /*
    * DESCRIPTION: Request Body For Creating/Updating Request Body
    * */
    class ConfiguredParts(
        val partId: String,
        val quantity: Integer
    )

    class CreateOrUpdateVehicleConfig(
        val plantId: Integer,
        val configurationId: String,
        val configurationTitle: String?,
        val description: String?,
        val grossWeight: String?,
        val steeringType: String?,
        val applicableColors: List<String>?,
        val noOfTyres: Integer?,
        val tyreSize: Float?,
        val configuredParts: List<ConfiguredParts>?
    ) {
        fun hasAnyNull(): Boolean {
            return listOf(
                configurationId,
                configurationTitle,
                description,
                grossWeight,
                steeringType
            ).any { it.isNullOrEmpty() } ||  // For Strings
                    listOf(
                        applicableColors,
                    ).any { it.isNullOrEmpty() } ||  // For Lists
                    noOfTyres == null ||              // Integer
                    tyreSize == null               // Float

        }
    }

    /*
    * DESCRIPTION:
    * */
    class GetVehicleConfiguration(
        val plantIds: List<String>?
    )

}