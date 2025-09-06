package com.vehiforge.userService.core.users.controllers

import com.vehiforge.userService.core.users.dto.UsersRequestBody
import com.vehiforge.userService.core.users.services.RoleServices
import com.vehiforge.userService.core.users.services.UserRoleService
import com.vehiforge.userService.helpers.common_dto.GenerateJsonResponse
import com.vehiforge.userService.helpers.exceptions.CustomException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/roles")
class RolesControllers(
    val roleServices: RoleServices,
    val userRoleService: UserRoleService
) {

    // Create Role
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN_READ_WRITE')")
    fun createRole(@RequestBody createRole: UsersRequestBody.CreateRole): GenerateJsonResponse<String> {

        try {

            roleServices.createRole(createRole.roleName, createRole.roleDesc)

            return GenerateJsonResponse(
                200,
                message = "Role Create Successfully",
                errorMessage = "",
                result = "Role Created"
            )

        }
        catch(customException: CustomException){
            return GenerateJsonResponse(
                status = customException.status,
                message = "",
                errorMessage = customException.errorMessage,
                result = ""
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                status = 500,
                message = "",
                errorMessage = exception.message,
                result = ""
            )
        }

    }


    /*
    * Description:
    * Assign To:
    * */
    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN_READ_WRITE')")
    fun updateUserRole(@RequestBody updateUserRole: UsersRequestBody.UpdateUserRole): GenerateJsonResponse<String> {

        try {

            return GenerateJsonResponse(
                200,
                message = userRoleService.updateRoleOfUser(
                    updateUserRole.userId,
                    updateUserRole.roleName,
                    updateUserRole.permissionType,
                    updateUserRole.updateType,
                ),
                errorMessage = "",
                null
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

}