package com.vehiforge.userService.helpers.exceptions;

class CustomException (
    val status: Number = 500,
    val errorMessage: String = "Internal Server Error"
) : RuntimeException(errorMessage)
