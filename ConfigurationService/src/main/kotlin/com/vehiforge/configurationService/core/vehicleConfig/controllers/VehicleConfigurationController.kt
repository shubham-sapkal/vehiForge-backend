package com.vehiforge.configurationService.core.vehicleConfig.controllers

import com.vehiforge.configurationService.core.vehicleConfig.dto.VehicleConfigRequestBody
import com.vehiforge.configurationService.core.vehicleConfig.models.ColorMaster
import com.vehiforge.configurationService.core.vehicleConfig.models.PlantMaster
import com.vehiforge.configurationService.core.vehicleConfig.models.VehicleConfiguration
import com.vehiforge.configurationService.core.vehicleConfig.services.ColorMasterService
import com.vehiforge.configurationService.core.vehicleConfig.services.VehicleConfigurationService
import com.vehiforge.configurationService.helpers.common_dto.GenerateJsonResponse
import com.vehiforge.configurationService.helpers.exceptions.CustomException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/config")
class VehicleConfigurationController(
    val colorMasterService: ColorMasterService,
    val vehicleConfigurationService: VehicleConfigurationService,
) {

    /*
    * DESCRIPTION: Create Or Update Plant Master
    * */
    @PostMapping("/plant-master")
    fun createOrUpdatePlantMaster(@RequestBody plantMaster: PlantMaster): GenerateJsonResponse<String> {

        try {
            return GenerateJsonResponse(
                status = 200,
                message = vehicleConfigurationService.createOrUpdatePlantMaster(plantMaster),
                errorMessage ="",
                result = ""

            )
        }
        catch(customException: CustomException){
            return GenerateJsonResponse(
                status = customException.status,
                message = "",
                errorMessage = customException.errorMessage,
                result = null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                status = 500,
                message = "",
                errorMessage = exception.message,
                result = null
            )
        }

    }

    /*
    * DESCRIPTION: Get Plant Master
    * */
    @GetMapping("/plant-master")
    fun getPlantMaster(): GenerateJsonResponse<List<PlantMaster>> {
        try {
            return GenerateJsonResponse(
                status = 200,
                message = "",
                errorMessage ="",
                result = vehicleConfigurationService.getPlantMaster()

            )
        }
        catch(customException: CustomException){
            return GenerateJsonResponse(
                status = customException.status,
                message = "",
                errorMessage = customException.errorMessage,
                result = null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                status = 500,
                message = "",
                errorMessage = exception.message,
                result = null
            )
        }
    }

    /*
    * TODO: Delete Plant Master
    * */


    /*
    * Add Vehicle Color
    * */
    @PostMapping("/color-master")
    fun addColor(@RequestBody color: ColorMaster): GenerateJsonResponse<Boolean> {

        try {

            return GenerateJsonResponse(
                200,
                message = "Color Added Successfully!",
                errorMessage = "",
                colorMasterService.addColor(color)
            )

        }
        catch(customException: CustomException){
            return GenerateJsonResponse(
                status = customException.status,
                message = "",
                errorMessage = customException.errorMessage,
                result = null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                status = 500,
                message = "",
                errorMessage = exception.message,
                result = null
            )
        }

    }

    /*
    * Get Show Color
    * */
    @GetMapping("/color-master")
    fun getColor(): GenerateJsonResponse<List<ColorMaster>> {

        try {

            return GenerateJsonResponse(
                200,
                message = "Color Fetched Successfully!",
                errorMessage = "",
                colorMasterService.getColor()
            )

        }
        catch(customException: CustomException){
            return GenerateJsonResponse(
                status = customException.status,
                message = "",
                errorMessage = customException.errorMessage,
                result = null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                status = 500,
                message = "",
                errorMessage = exception.message,
                result = null
            )
        }

    }

    /*
    * Create Or Update Vehicle Config
    * */
    @PostMapping("/vehicle-config")
    fun createOrUpdateVehicleConfiguration(@RequestBody vehicleConfig: VehicleConfigRequestBody.CreateOrUpdateVehicleConfig): GenerateJsonResponse<String> {

        try {

            return GenerateJsonResponse(
                200,
                message = "Configuration Updated Successfully!",
                errorMessage = "",
                vehicleConfigurationService.createOrUpdateConfiguration(vehicleConfig)
            )

        }
        catch(customException: CustomException){
            println("Custom Exception: $customException")
            return GenerateJsonResponse(
                status = customException.status,
                message = "",
                errorMessage = customException.errorMessage,
                result = null
            )
        }
        catch (exception: Exception) {
            println("Exception: $exception")
            return GenerateJsonResponse(
                status = 500,
                message = "",
                errorMessage = exception.message,
                result = null
            )
        }

    }

    @PostMapping("/get-vehicle-config")
    fun getVehicleConfiguration(@RequestBody request: VehicleConfigRequestBody.GetVehicleConfiguration): GenerateJsonResponse<List<VehicleConfiguration>> {

        try {

            return GenerateJsonResponse(
                200,
                message = "Color Fetched Successfully!",
                errorMessage = "",
                vehicleConfigurationService.getConfigurationParts(request)
            )

        }
        catch(customException: CustomException){
            println("Custom Exception: $customException")
            return GenerateJsonResponse(
                status = customException.status,
                message = "",
                errorMessage = customException.errorMessage,
                result = null
            )
        }
        catch (exception: Exception) {
            println("Exception: $exception")
            return GenerateJsonResponse(
                status = 500,
                message = "",
                errorMessage = exception.message,
                result = null
            )
        }

    }

}