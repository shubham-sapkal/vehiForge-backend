package com.vehiforge.configurationService.core.vehicleConfig.services

import com.vehiforge.configurationService.core.vehicleConfig.models.ColorMaster
import com.vehiforge.configurationService.core.vehicleConfig.repositories.ColorMasterRepository
import org.springframework.stereotype.Service

@Service
class ColorMasterService(
    val colorMasterRepository: ColorMasterRepository
) {

    /*
    *  Add Color
    * */
    fun addColor(color: ColorMaster): Boolean {

        colorMasterRepository.save(color);
        return true;

    }

    /*
    *  Get Colors
    * */
    fun getColor(): List<ColorMaster> {
        return colorMasterRepository.findAll();
    }

}