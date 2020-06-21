package com.meow.rentalz_kotlin.utils

enum class ErrorType(val code: Int) {
    NONE(0), INVALID_FORMAT(1), ILLEGAL_DATA(2)
}