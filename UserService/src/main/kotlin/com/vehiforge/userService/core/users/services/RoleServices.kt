package com.vehiforge.userService.core.users.services

import com.vehiforge.userService.core.users.models.Roles
import com.vehiforge.userService.core.users.repositories.RoleRepository
import com.vehiforge.userService.helpers.exceptions.CustomException
import org.springframework.stereotype.Service

@Service
class RoleServices(
    val roleRepository: RoleRepository
) {
    // Get All Available Role

    // Create Role
    fun createRole(roleName: String, roleDesc: String): Roles {

        // Check if role exists
        if(roleRepository.existsById(roleName)) {
            throw CustomException(409,"Role Already Exists")
        }

        // Create Role in table
        return roleRepository.save(Roles(roleName=roleName, roleDescription=roleDesc))

    }

}