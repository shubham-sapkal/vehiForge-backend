package com.vehiforge.userService.core.users.repositories

import com.vehiforge.userService.core.users.models.PermissionType
import com.vehiforge.userService.core.users.models.UserRole
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface UserRoleRepository: JpaRepository<UserRole, UUID> {

    /*
    *    Update User Role Based on username and roleName
    * */
    @Modifying
    @Transactional
    @Query("""
       UPDATE UserRole ur 
       SET ur.permissionType = :permissionType
       WHERE ur.user.username = :username AND ur.role.roleName = :roleName 
    """)
    fun updatePermissionType(username: String, roleName: String, permissionType: PermissionType ): Number

    /*
    *  Delete User Role Based on Username and roleName
    * */
    @Modifying
    @Transactional
    @Query("""
       DELETE FROM UserRole ur
       WHERE ur.user.username = :username AND ur.role.roleName = :roleName 
    """)
    fun deleteUserRole(username: String, roleName: String ): Number

}