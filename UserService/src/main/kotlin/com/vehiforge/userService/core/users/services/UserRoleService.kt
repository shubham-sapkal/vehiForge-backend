package com.vehiforge.userService.core.users.services

import com.vehiforge.userService.core.users.constants.RoleUpdateTypes
import com.vehiforge.userService.core.users.models.PermissionType
import com.vehiforge.userService.core.users.models.UserRole
import com.vehiforge.userService.core.users.models.Users
import com.vehiforge.userService.core.users.repositories.RoleRepository
import com.vehiforge.userService.core.users.repositories.UserRoleRepository
import com.vehiforge.userService.core.users.repositories.UsersRepositories
import com.vehiforge.userService.helpers.exceptions.CustomException
import org.springframework.stereotype.Service

@Service
class UserRoleService(
    val usersRepositories: UsersRepositories,
    val roleRepository: RoleRepository,
    val userRoleRepository: UserRoleRepository
) {

    // Service Fun for: Assigning Role to Use
    fun assignRoleToUser(user: Users, roleName: String, permissionType: PermissionType) {

//        val user = usersRepositories.findById(userId).orElseThrow { CustomException(404, "User Not Found!") }
        val role = roleRepository.findById(roleName).orElseThrow { CustomException(404, "Role Not Found!") }

        val userRole = UserRole(
            user = user,
            role = role,
            permissionType = permissionType
        )
        userRoleRepository.save(userRole)

    }

    // Service Fun for: Updating User Role
    fun updateRoleOfUser(userId: String, roleName: String, permissionType: PermissionType, updateType: RoleUpdateTypes): String {

        val user = usersRepositories.findById(userId).orElseThrow { CustomException(404, "User Not Found!") }
       //  val role = roleRepository.findById(roleName).orElseThrow { CustomException(404, "Role Not Found!") }

        var responseText: String

        when(updateType) {
            RoleUpdateTypes.ADD -> {
                assignRoleToUser(user, roleName, permissionType)
                responseText = "Role Added Successfully!"
            }
            RoleUpdateTypes.UPDATE -> {
                val updateCount = userRoleRepository.updatePermissionType(userId, roleName, permissionType)
                responseText = "Role Update Successfully for '$updateCount' times"
            }
            RoleUpdateTypes.DELETE -> {
                val deleteCount = userRoleRepository.deleteUserRole(userId, roleName)
                responseText = "Role Deleted Successfully for '$deleteCount' times"
            }
//            else -> {
//                throw CustomException(404, "Update Type '$updateType' not Found ")
//            }
        }

        return responseText

    }

}