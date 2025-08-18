package com.vehiforge.userService.helpers.common_dto

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class GenerateJsonResponse<T> (
    val status: Number = 200,
    val message: String = "Default Message",
    val errorMessage: String? = null,
    val result: T? = null
)