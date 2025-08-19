package com.vehiforge.userService.core.users.services

import com.vehiforge.userService.core.users.dto.UsersRequestBody
import com.vehiforge.userService.core.users.models.PermissionType
import com.vehiforge.userService.core.users.models.Users
import com.vehiforge.userService.core.users.repositories.UsersRepositories
import com.vehiforge.userService.helpers.exceptions.CustomException
import org.springframework.stereotype.Service

@Service
class UserServices(
    val usersRepositories: UsersRepositories,
    val userRoleService: UserRoleService
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
    fun login(loginUser: UsersRequestBody.loginUser): String {

        // Check if user exist
        val users = usersRepositories.findById(loginUser.username).orElseThrow { CustomException(404, "Username Not Found!") }

        if( users.password.equals(loginUser.password) ) {

            // Return JWT Auth Token
            return "asdsa"

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