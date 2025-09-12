package com.coursy.platforms.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ConfigService {
    fun getNavbarConfig(): String {
        // TODO implement
        return ""
    }

    fun getFooterConfig(): String {
        // TODO implement
        return ""
    }
}