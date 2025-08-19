package com.vehiforge.userService.core.users.dto

import com.vehiforge.userService.core.users.constants.RoleUpdateTypes
import com.vehiforge.userService.core.users.models.PermissionType

class UsersRequestBody {

    /*
    *   Roles Request Body
    * */

    class CreateRole(
        val roleName: String,
        val roleDesc: String
    )

    class UpdateUserRole(
        val userId: String,
        val roleName: String,
        val permissionType: PermissionType,
        val updateType: RoleUpdateTypes
    )

    /*
    * Users Request Body
     */

    class AddRoles(
        val roleName: String,
        val permissionType: PermissionType
    )

    class CreateUser(
        val username: String,
        val email: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val phoneNo: String,
        val roles: List<AddRoles>?
    )

    // Login User
    class loginUser(
        val username: String,
        val password: String
    )

}