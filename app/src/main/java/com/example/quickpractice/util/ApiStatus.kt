package com.example.quickpractice.util

enum class ApiStatus(val value: Int) {
    SUCCESS(200),
    FAILURE(400),
    NOT_FOUND(404),
    SERVER_ERROR(500);
}