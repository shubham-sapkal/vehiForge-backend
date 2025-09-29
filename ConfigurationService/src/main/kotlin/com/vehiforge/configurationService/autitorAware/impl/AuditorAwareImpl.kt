package com.vehiforge.configurationService.autitorAware.impl

import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.Optional

@Component("auditorProvider")
class AuditorAwareImpl: AuditorAware<String> {

    override fun getCurrentAuditor(): Optional<String> {

        // TODO: Get the user info from SecurityContext

        return Optional.of("system")

    }

}