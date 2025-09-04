package com.vehiforge.userService.core.security.services

import com.vehiforge.userService.core.users.repositories.UsersRepositories
import com.vehiforge.userService.helpers.exceptions.CustomException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val usersRepositories: UsersRepositories
): UserDetailsService {

    /*
    * Description: This function will return UserDetails Object with username, its password and its authorities
    * */
    override fun loadUserByUsername(username: String): UserDetails {

        // Get Users Instance on username
        val user = usersRepositories.findById(username).orElseThrow { CustomException(404, "User Not Found!") }

        // Get the user Authorities
        val authories = user.roles.map {
            SimpleGrantedAuthority("ROLE_${it.role.roleName}_${it.permissionType}")
        }.toSet()

        return User(
            user.username,
            user.password,
            authories
        )

    }

}