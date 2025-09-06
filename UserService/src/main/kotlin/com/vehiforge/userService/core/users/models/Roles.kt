package com.vehiforge.userService.core.users.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name="roles")
class Roles (
    @Id
    @Column(name = "role_name")
    val roleName: String,

    @Column(nullable = true)
    val roleDescription: String,

    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val userRoles: MutableList<UserRole> = mutableListOf()
)