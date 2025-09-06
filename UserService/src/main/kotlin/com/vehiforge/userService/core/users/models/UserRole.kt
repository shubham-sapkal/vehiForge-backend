package com.vehiforge.userService.core.users.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID


@Entity
@Table(name="user_role")
data class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    @JsonIgnore
    val user: Users,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_name")   // FK points to Role.roleName
    @JsonIgnore
    val role: Roles,

    @Enumerated(EnumType.STRING)
    val permissionType: PermissionType
) {
    val roleName: String
        get() = role.roleName

    val userName: String
        get() = user.username

    val roleDescription: String
        get() = role.roleDescription
}