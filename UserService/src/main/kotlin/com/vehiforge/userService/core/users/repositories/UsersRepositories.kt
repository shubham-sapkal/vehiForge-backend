package com.vehiforge.userService.core.users.repositories

import com.vehiforge.userService.core.users.models.Users
import org.springframework.data.jpa.repository.JpaRepository

interface UsersRepositories: JpaRepository<Users, String> {
}