package com.vehiforge.userService.core.users.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table


@Entity
@Table(name = "users")
class Users (

    @Id
    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = true)
    var password: String,

    @Column(nullable = true)
    var firstName: String,

    @Column(nullable = true)
    var lastName: String,

    @Column(nullable = true)
    var phoneNo: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    var roles: MutableList<UserRole> = mutableListOf(),
    )