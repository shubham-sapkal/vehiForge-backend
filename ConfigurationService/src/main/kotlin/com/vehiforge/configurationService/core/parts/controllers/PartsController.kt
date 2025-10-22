package com.vehiforge.configurationService.core.parts.controllers

import com.vehiforge.configurationService.core.parts.dto.PartsRequestDto
import com.vehiforge.configurationService.core.parts.models.PartFamilyDetails
import com.vehiforge.configurationService.core.parts.models.PartsMaster
import com.vehiforge.configurationService.core.parts.repositories.PartsMasterRepository
import com.vehiforge.configurationService.core.parts.services.PartsServices
import com.vehiforge.configurationService.helpers.common_dto.GenerateJsonResponse
import com.vehiforge.configurationService.helpers.exceptions.CustomException
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/parts")
class PartsController(
    val partsServices: PartsServices,

) {

    /*
    * Create or Update Part Family
    * */
    @PostMapping("/part-family")
    fun createOrUpdatePartFamily(@RequestBody createOrUpdatePartFamily: PartsRequestDto.CreateOrUpdatePartFamily): GenerateJsonResponse<String> {

        try {
            return GenerateJsonResponse(
                200,
                partsServices.createOrUpdatePartFamily(createOrUpdatePartFamily),
                "",
                null
            )
        }
        catch (ce: CustomException) {
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        }
        catch (e: Exception) {
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }

    }

    /*
   * Description: Get Parts Family Details
   * */
    @GetMapping("/part-family")
    fun getPartsFamilies(): GenerateJsonResponse<List<PartFamilyDetails>> {

        try {
            return GenerateJsonResponse(
                200,
                "Part Families Retrived Successfully!",
                "",
                partsServices.getPartFamilies()
            )
        }
        catch (ce: CustomException) {
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        }
        catch (e: Exception) {
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }
    }

    /*
    * TODO: Bulk Create or Update Part Family using excel/csv sheet
    * */

    /*
    * Delete Part Family
    * */
    @DeleteMapping("/part-family")
    fun deletePartFamily(@RequestBody deletePartFamily: PartsRequestDto.DeletePartFamily): GenerateJsonResponse<String> {

        try {
            return GenerateJsonResponse(
                200,
                partsServices.deletePartFamily(deletePartFamily.partFamilyName),
                "",
                null
            )
        } catch (ce: CustomException) {
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        } catch (e: Exception) {
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }
    }


    /*
    * Get All Parts
    * */
    @GetMapping("/parts-master")
    fun getParts(): GenerateJsonResponse<List<PartsMaster>> {

        try {
            return GenerateJsonResponse(
                200,
                "Parts Retrived Successfully!",
                "",
                partsServices.getParts()
            )
        }
        catch (ce: CustomException) {
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        }
        catch (e: Exception) {
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }

    }


    /*
    * Get All Parts Of Plant
    * */
    @PostMapping("/parts-master/filters")
    fun getPartsOfPlant(@RequestBody filter: PartsRequestDto.GetPartsOfPlants): GenerateJsonResponse<List<PartsMaster>> {

        try {
            return GenerateJsonResponse(
                200,
                "Parts Retrived Successfully!",
                "",
                partsServices.getPartsOfPlants(filter)
            )
        }
        catch (ce: CustomException) {
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        }
        catch (e: Exception) {
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }

    }

    /*
    * Description: Create or Update Part
    * */
    @PostMapping("/parts-master")
    fun createOrUpdatePart(@RequestBody partMaster: PartsRequestDto.CreateOrUpdatePartsMaster): GenerateJsonResponse<String> {

        try {
            return GenerateJsonResponse(
                200,
                partsServices.createOrUpdatePart(partMaster),
                "",
                null
            )
        }
        catch (ce: CustomException) {
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        }
        catch (e: Exception) {
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }

    }


    /*
    * DESCRIPTION: Delete Part
    * */
    @DeleteMapping("/parts-master")
    fun deletePartMaster(@RequestBody partId: PartsRequestDto.DeletePartMaster): GenerateJsonResponse<String> {

        try {

            return GenerateJsonResponse(
                200,
                partsServices.deletePart(partId),
                "",
                null
            )
        }
        catch (ce: CustomException) {
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        }
        catch (e: Exception) {
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }

    }

    /*
    * Description:
    * */
    fun getPartsList(@RequestBody partsList: List<String>): GenerateJsonResponse<List<PartsMaster>> {
        try {
            return GenerateJsonResponse(
                200,
                "Parts Fetched Successfully!",
                "",
                partsServices.getListOfParts(partsList)
            )
        }
        catch (ce: CustomException) {
            println("CustomException: $ce")
            return GenerateJsonResponse(
                ce.status,
                ce.message ?: "",
                ce.errorMessage,
                null
            )
        }
        catch (e: Exception) {
            println("Exception: $e")
            return GenerateJsonResponse(
                500,
                "",
                e.message,
                null
            )
        }
    }

}