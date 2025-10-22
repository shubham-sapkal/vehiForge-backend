package com.vehiforge.configurationService.core.vehicleConfig.services

import com.vehiforge.configurationService.core.parts.models.PartsMaster
import com.vehiforge.configurationService.core.parts.services.PartsServices
import com.vehiforge.configurationService.core.vehicleConfig.dto.VehicleConfigRequestBody
import com.vehiforge.configurationService.core.vehicleConfig.models.ConfiguredParts
import com.vehiforge.configurationService.core.vehicleConfig.models.PlantMaster
import com.vehiforge.configurationService.core.vehicleConfig.models.VehicleConfiguration
import com.vehiforge.configurationService.core.vehicleConfig.repositories.ColorMasterRepository
import com.vehiforge.configurationService.core.vehicleConfig.repositories.PlantMasterRepository
import com.vehiforge.configurationService.core.vehicleConfig.repositories.VehicleConfigurationRepository
import com.vehiforge.configurationService.kafka.producer.KafkaMessageProducer
import org.springframework.stereotype.Service

@Service
class VehicleConfigurationService(
    val vehicleConfigurationRepository: VehicleConfigurationRepository,
    val plantMasterRepository: PlantMasterRepository,
    val partsServices: PartsServices,
    val colorMasterRepository: ColorMasterRepository,
    val kafkaMessageProducer: KafkaMessageProducer
) {

    /*
    * DESCRIPTION: Create Or Update Plant Master
    * */
    fun createOrUpdatePlantMaster(plant: PlantMaster): String {

       val existingPlant = plantMasterRepository.findById(plant.plantId).orElse(null)

        if (existingPlant != null) {
            // Update existing
            existingPlant.plantName = plant.plantName
            existingPlant.plantLocation = plant.plantLocation
            plantMasterRepository.save(existingPlant)
        } else {
            // Create new
            plantMasterRepository.save(plant)
        }

        return "Plant Master Created/Updated Successfully!"


    }

    /*
    * DESCRIPTION: Get Plant Master
    * */
    fun getPlantMaster(): List<PlantMaster> {
        return plantMasterRepository.findAll()
    }


    /*
    * DESCRIPTION: Create or Update Configuration
    * */
    fun createOrUpdateConfiguration(dto: VehicleConfigRequestBody.CreateOrUpdateVehicleConfig): String {

        var changeType: String = "UPDATED";

        // Get Applicable Colors
        val colors = dto.applicableColors?.mapNotNull { colorName ->
            colorMasterRepository.findById(colorName).orElse(null)
        }?.toMutableList() ?: mutableListOf()

        val vehicleConfig = vehicleConfigurationRepository.findById(dto.configurationId)
            .orElseGet {
                // CREATE: all mandatory fields must be present
                if (dto.hasAnyNull()) {
                    throw IllegalArgumentException("All mandatory fields must be provided for creation")
                }
                changeType = "ADDED";
                VehicleConfiguration(
                    plantId = dto.plantId,
                    configurationId = dto.configurationId,
                    configurationTitle = dto.configurationTitle!!,
                    description = dto.description!!,
                    applicableColors = colors,
                    grossWeight = dto.grossWeight!!,
                    steeringType = dto.steeringType!!,
                    noOfTyres = dto.noOfTyres!!,
                    tyreSize = dto.tyreSize!!
                )

            }

        // UPDATE: only update non-null fields
        dto.plantId.let { vehicleConfig.plantId = it }
        dto.configurationTitle?.let { vehicleConfig.configurationTitle = it }
        dto.description?.let { vehicleConfig.description = it }
        dto.grossWeight?.let { vehicleConfig.grossWeight = it }
        dto.steeringType?.let { vehicleConfig.steeringType = it }
        dto.noOfTyres?.let { vehicleConfig.noOfTyres = it }
        dto.tyreSize?.let { vehicleConfig.tyreSize = it }

        // Update applicableColors if provided
        dto.applicableColors?.let { vehicleConfig.applicableColors = colors }

        // Update configuredParts if provided
        dto.configuredParts?.let { partDtos ->
            val parts = partsServices.getListOfParts(partDtos.map { it.partId })
            vehicleConfig.configuredParts?.clear()
            partDtos.forEach { partDto ->
                val part = parts.find { it.partId == partDto.partId }
                    ?: throw IllegalArgumentException("Part not found: ${partDto.partId}")
                vehicleConfig.configuredParts?.add(
                    ConfiguredParts(
                        vehicleConfiguration = vehicleConfig,
                        mappedPart = part,
                        quantity = partDto.quantity
                    )
                )
            }
        }

        vehicleConfigurationRepository.save(vehicleConfig)

        kafkaMessageProducer.sendVehicleConfigChangeKafkaMsg(
            changeType,
            vehicleConfig.configurationId,
            vehicleConfig
        )

        return "Configuration Added/Updated Successfully!"

    }

    /*
    * DESCRIPTION: Get Configured Parts
    * */
    fun getConfigurationParts(requestBody: VehicleConfigRequestBody.GetVehicleConfiguration): List<VehicleConfiguration> {

        if(requestBody.plantIds?.isNotEmpty() == true ) {

            return vehicleConfigurationRepository.findByPlantIdIn(requestBody.plantIds);

        }

        return vehicleConfigurationRepository.findAll()

    }

}