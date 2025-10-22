package com.vehiforge.configurationService.core.parts.services

import com.vehiforge.configurationService.core.parts.dto.PartsRequestDto
import com.vehiforge.configurationService.core.parts.models.PartFamilyDetails
import com.vehiforge.configurationService.core.parts.models.PartsMaster
import com.vehiforge.configurationService.core.parts.repositories.PartFamilyDetailsRepository
import com.vehiforge.configurationService.core.parts.repositories.PartsMasterRepository
import com.vehiforge.configurationService.helpers.exceptions.CustomException
import com.vehiforge.configurationService.kafka.producer.KafkaMessageProducer
import org.springframework.stereotype.Service
import java.util.Date

@Service
class PartsServices(
    val partFamilyDetailsRepository: PartFamilyDetailsRepository,
    val partsMasterRepository: PartsMasterRepository,
    // Kafka Message Producer
    val kafkaMessageProducer: KafkaMessageProducer
) {

    /*
    * Description: Create or Update Part Family
    * */
    fun createOrUpdatePartFamily(partFamilyDetail: PartsRequestDto.CreateOrUpdatePartFamily): String {

        try {

            // Check if Parts is available
            val optionalPartFamily = partFamilyDetailsRepository.findById(partFamilyDetail.partFamilyName)

            // if available then update that parts
            if(optionalPartFamily.isPresent) {

                val partFamily = optionalPartFamily.get().apply {
                    this.partFamilyDescription = partFamilyDetail.partFamilyDescription
                    this.applicableVehicleType = partFamilyDetail.applicableVehicleType
                }

                partFamilyDetailsRepository.save(partFamily)

                return "Part Family Update Successfully!"

            } else {
                // else create new part Family
                partFamilyDetailsRepository.save(PartFamilyDetails(
                    partFamilyName = partFamilyDetail.partFamilyName,
                    partFamilyDescription = partFamilyDetail.partFamilyDescription,
                    applicableVehicleType = partFamilyDetail.applicableVehicleType
                ))

                return "Part Family Created Successfully!"

            }

        } catch (e: Exception) {
            throw CustomException(500, e.message ?: "Internal Server Error!")
        }

    }

    /*
    * TODO: Get Parts Family Details
    * */
    fun getPartFamilies(): List<PartFamilyDetails> {
        return partFamilyDetailsRepository.findAll();
    }

    /*
    * TODO: Bulk Create or Update Part Family using excel/csv sheet
    * */

    /*
    * Description: Delete Part Family
    * */
    fun deletePartFamily(partFamilyName: String): String {

        try {

            val partFamilyDetails = partFamilyDetailsRepository.findById(partFamilyName)
                .orElseThrow{ CustomException(204, "Part Family $partFamilyName not Found!") }

            partFamilyDetailsRepository.delete(partFamilyDetails)

            return "Part Family $partFamilyName deleted successfully!"

        }
        catch (e: Exception) {
            throw CustomException(500, e.message ?: "Internal Server Error!")
        }

    }


    /*
    * TODO: Get Part Families
    * */


    /*
    * TODO: Get All Parts
    * */
    fun getParts(): List<PartsMaster> {
        return partsMasterRepository.findAll()
    }


    /*
    * TODO: Create or Update Part
    * */
    fun createOrUpdatePart(updatedParts: PartsRequestDto.CreateOrUpdatePartsMaster): String {

        val optionalPart = partsMasterRepository.findByPlantIdAndPartId(updatedParts.plantId, updatedParts.partId)

        val updatedPartFamily = partFamilyDetailsRepository.findById(updatedParts.partFamilyName).orElseThrow { CustomException(404, "Part Family Not Found!") }

        if(optionalPart.isPresent) {

            val partMaster = optionalPart.get()

            val updatedPartMaster = partMaster.apply {
                updatedParts.partName.let { this.partName = updatedParts.partName }
                updatedParts.partName.let { this.partDescription = updatedParts.partDescription }
                updatedParts.partName.let { this.partFamily = updatedPartFamily }
                updatedParts.standardCost.let {
                    if(this.standardCost != updatedParts.standardCost)
                        this.standardCost = updatedParts.standardCost
                        this.lastCostUpdateAt = Date()
                }
            }

            partsMasterRepository.save(updatedPartMaster)

            // Generate Kafka Message
            kafkaMessageProducer.sendPartMasterAddedOrUpdated("UPDATED", updatedPartMaster.partId, updatedPartMaster);

            return "Part Master Updated Successfully!"

        }
        else{
            val createdPartMaster = partsMasterRepository.save(PartsMaster(
                plantId = updatedParts.plantId,
                partId = updatedParts.partId,
                partName = updatedParts.partName,
                partDescription = updatedParts.partDescription,
                partFamily = updatedPartFamily,
                standardCost = updatedParts.standardCost,
                lastCostUpdateAt = Date()
            ))

            // Generate Kafka Message
            kafkaMessageProducer.sendPartMasterAddedOrUpdated("ADDED", createdPartMaster.partId, createdPartMaster);

            return "Part Master Created Successfully!"
        }

    }


    /*
    * TODO: Delete Part
    * */
    fun deletePart(partDetails: PartsRequestDto.DeletePartMaster): String {

//        val part = partsMasterRepository.findByPlantIdAndPartIdIsDeletedFalse(partDetails.plantId, partDetails.partId) ?: throw CustomException(404, "Part Not Found for ${partDetails.plantId} ${partDetails.partId} !")
//
//        //  Soft Delete the Part
//        part.isDeleted = true
//        part.deletedBy = "System"
//
//        partsMasterRepository.save(part)
//
//        kafkaMessageProducer.sendPartMasterAddedOrUpdated(
//            "DELETED",
//            part.partId,
//            part
//        )

        return "Part Deleted Successfully!"

    }

    /*
    * DESCRIPTION: Get Parts On List Of String
    * */
    fun getListOfParts(partsList: List<String>): List<PartsMaster> {
        return partsMasterRepository.findByPartIdIn(partsList);
    }

    /*
    * DESCRIPTION: Get Parts Of Specific Plant
    * */
    fun getPartsOfPlants(filter: PartsRequestDto.GetPartsOfPlants): List<PartsMaster> {

        return partsMasterRepository.findByPlantIdIn(filter.plantIds)

    }

}