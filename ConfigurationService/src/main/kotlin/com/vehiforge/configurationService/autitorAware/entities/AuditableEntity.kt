package com.vehiforge.configurationService.autitorAware.entities

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class AuditableEntity {

    @CreatedDate
    @Column(nullable = false)
    var createdAt: Instant? = null

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: Instant? = null

    @CreatedBy
    @Column(nullable = false)
    var createdBy: String? = null

    @LastModifiedBy
    @Column(nullable = false)
    var updatedBy: String? = null

}