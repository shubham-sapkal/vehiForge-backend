package com.vehiforge.configurationService.core.security.introspects

import com.vehiforge.configurationService.helpers.common_dto.GenerateJsonResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody



@FeignClient(name="vehiForge-userService", url="\${security.user-service-url}")
interface IntrospectClient {

    @PostMapping("/users/introspect")
    fun introspect(@RequestBody req: TokenValidationReq): GenerateJsonResponse<TokenValidationResp>

}

data class TokenValidationReq(
    val token: String
)

data class TokenValidationResp(
    val isValid: Boolean,
    val username: String?,
    val roles: List<String> = emptyList()
)