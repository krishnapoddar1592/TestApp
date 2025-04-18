package com.reflect.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform