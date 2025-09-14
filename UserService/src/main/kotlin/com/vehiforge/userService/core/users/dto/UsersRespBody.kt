package com.vehiforge.userService.core.users.dto

import com.vehiforge.userService.core.users.models.PermissionType

class UsersRespBody {
    /*
    * Login User Response Body
    * */

    class GetRoles(
        val roleName: String,
        val permissionType: PermissionType
    )

    class LoginUserRespBody(
        val username: String,
        val email: String,
        val firstName: String,
        val lastName: String,
        val roles: List<GetRoles>? = null,
        val authToken: String
    )
}