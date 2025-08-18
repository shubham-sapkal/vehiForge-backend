package com.vehiforge.userService.core.users.repositories

import com.vehiforge.userService.core.users.models.Roles
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Roles, String> {
}