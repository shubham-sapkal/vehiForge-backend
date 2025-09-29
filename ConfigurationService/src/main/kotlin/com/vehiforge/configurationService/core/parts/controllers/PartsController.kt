package com.vehiforge.configurationService.core.parts.controllers

import com.vehiforge.configurationService.core.parts.dto.PartsRequestDto
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
   * TODO: Get Parts Family Details
   * */

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
    * TODO: Get Part Families
    * */


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
    * TODO: Delete Part
    * */

}