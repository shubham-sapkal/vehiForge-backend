package com.vehiforge.userService.core.users.services

import com.vehiforge.userService.core.security.services.JwtService
import com.vehiforge.userService.core.users.dto.UsersRequestBody
import com.vehiforge.userService.core.users.dto.UsersRespBody
import com.vehiforge.userService.core.users.dto.UsersRespBody.GetRoles
import com.vehiforge.userService.core.users.models.PermissionType
import com.vehiforge.userService.core.users.models.Users
import com.vehiforge.userService.core.users.repositories.UsersRepositories
import com.vehiforge.userService.helpers.exceptions.CustomException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import kotlin.String

@Service
class UserServices(
    private val usersRepositories: UsersRepositories,
    private val userRoleService: UserRoleService,

    private val jwtService: JwtService
) {

    // Service Fun for: Create User
    fun createUser(newUser: UsersRequestBody.CreateUser): Users {

        // Check If User Exists
        if(usersRepositories.existsById(newUser.username)) {
            throw CustomException(409, "User Already Exists")
        }

        // Create User
        val user = Users(
            username = newUser.username,
            email = newUser.email,
            password = newUser.password,
            firstName = newUser.firstName,
            lastName = newUser.lastName,
            phoneNo = newUser.phoneNo
        )

        val createdUser = usersRepositories.save(user)

        // If roles is none then assign CUSTOMER role,
        if(newUser.roles == null) {
            userRoleService.assignRoleToUser(user, "CUSTOMER", PermissionType.READ_WRITE)
        }
        else { // Else give roles mentioned on roles
            newUser.roles.forEach { role -> userRoleService.assignRoleToUser(user, role.roleName, role.permissionType) }
        }

        // return user
        return createdUser

    }

    /*
    * Description: Service Function for login user
    * */
    fun login(loginUser: UsersRequestBody.loginUser): UsersRespBody.LoginUserRespBody {

        // Check if user exist
        val user = usersRepositories.findById(loginUser.username).orElseThrow { CustomException(404, "Username Not Found!") }

        if( user.password.equals(loginUser.password) ) {

            val authorities = user.roles.map {
                SimpleGrantedAuthority("ROLE_${it.role.roleName}_${it.permissionType}")
            }

            val details = User(
                user.username,
                user.password,
                authorities
            )

            return UsersRespBody.LoginUserRespBody(
                username = user.username,
                email = user.email,
                firstName = user.firstName,
                lastName = user.lastName,
                roles = user.roles.map { UsersRespBody.GetRoles(
                    roleName = it.roleName,
                    permissionType = it.permissionType
                ) },
                authToken = jwtService.generateToken(details)
            )

        }
        else{
            throw CustomException(400, "Password is incorrect!")
        }

    }


    // Service Fun for: Get All User
    fun getAllUser(): List<Users> {
        return usersRepositories.findAll()
    }

    // Service Fun for: Get User by username
    fun getUserByUserName(username: String): Users {
        return usersRepositories.findById(username).orElseThrow { CustomException(404, "User Not Found!") }
    }
}