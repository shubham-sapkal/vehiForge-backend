package com.vehiforge.bookingService.helpers.exceptions

class CustomException (
    val status: Number = 500,
    val errorMessage: String = "Internal Server Error"
) : RuntimeException(errorMessage)
