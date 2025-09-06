package com.vehiforge.userService.core.users.controllers

import com.vehiforge.userService.core.users.dto.UsersRequestBody
import com.vehiforge.userService.core.users.models.Users
import com.vehiforge.userService.core.users.services.UserServices
import com.vehiforge.userService.helpers.common_dto.GenerateJsonResponse
import com.vehiforge.userService.helpers.exceptions.CustomException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UsersControllers(
    val userServices: UserServices
) {

    /*
    * Description: API VIEW for Creating User
    * Accessible to: OPEN
    * */
    @PostMapping("/create")
    fun createUser(@RequestBody newUser: UsersRequestBody.CreateUser): GenerateJsonResponse<Users> {

        try {

            return GenerateJsonResponse(
                200,
                "User Created Successfully",
                "",
                userServices.createUser(newUser)
            )

        }
        catch (customException: CustomException) {
            return GenerateJsonResponse(
                customException.status,
                "Something Went Wrong",
                customException.errorMessage,
                null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                500,
                "Internal Server Error",
                exception.message,
                null
            )
        }
    }

    /*
    * Description: API to get user login return jwt token
    * Accessible to: OPEN
    * */
    @PostMapping("/login")
    fun login(@RequestBody loginUserReqBody: UsersRequestBody.loginUser): GenerateJsonResponse<String> {
        try{

            return GenerateJsonResponse(
                200,
                "Login Successful!",
                null,
                userServices.login(loginUserReqBody)
            )

        }
        catch (customException: CustomException) {
            return GenerateJsonResponse(
                customException.status,
                "Something Went Wrong",
                customException.errorMessage,
                null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                500,
                "Internal Server Error",
                exception.message,
                null
            )
        }
    }

    /*
    * Description: API to Get All User
    * Accessible to: SUPER_ADMIN
    * */
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN_READ_WRITE', 'ADMIN_READ_ONLY)")
    fun getAllUsers(): GenerateJsonResponse<List<Users>> {
        try {
            return GenerateJsonResponse(
                200,
                "Data Retrived Successfully!",
                "",
                userServices.getAllUser()
            )
        }
        catch (customException: CustomException) {
            return GenerateJsonResponse(
                customException.status,
                "Something Went Wrong",
                customException.errorMessage,
                null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                500,
                "Internal Server Error",
                exception.message,
                null
            )
        }
    }

    /*
    * Description:
    * Accessible To: OPEN
    * */
    @GetMapping("/{username}")
    fun getUserByUsername(@PathVariable username: String): GenerateJsonResponse<Users> {

        try {

            return GenerateJsonResponse(
                200,
                "User Found!",
                null,
                userServices.getUserByUserName(username)
            )

        }
        catch (customException: CustomException) {
            return GenerateJsonResponse(
                customException.status,
                "Something Went Wrong",
                customException.errorMessage,
                null
            )
        }
        catch (exception: Exception) {
            return GenerateJsonResponse(
                500,
                "Internal Server Error",
                exception.message,
                null
            )
        }

    }

}