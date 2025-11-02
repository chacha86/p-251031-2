package com.back.standard.ut

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

class Ut {
    companion object jwt {
        // Java에서 호출할 수 있도록 함. @JvmStatic이 없으면 자바에서 클래스명.Companion.메서드명()으로 호출해야 합니다.
        fun toString(secret: String, expireSeconds: Long, body: Map<String, Any>): String {

            val issuedAt = Date()
            val expiration = Date(issuedAt.time + 1000L * expireSeconds)

            val secretKey: Key = Keys.hmacShaKeyFor(secret.toByteArray())

            val jwt = Jwts.builder()
                .claims(body)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(secretKey)
                .compact()

            return jwt
        }

        fun isValid(jwt: String, secretPattern: String): Boolean {
            val secretKey = Keys.hmacShaKeyFor(secretPattern.toByteArray(StandardCharsets.UTF_8))

            return runCatching {
                Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(jwt)
                true
            }.getOrElse { false }

        }
        
        fun payloadOrNull(jwt: String, secretPattern: String): Map<String, Any>? {
            val secretKey = Keys.hmacShaKeyFor(secretPattern.toByteArray(StandardCharsets.UTF_8))

            return if (isValid(jwt, secretPattern)) {
                return Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parse(jwt)
                    .payload as Map<String, Any>
            } else {
                null
            }
        }
    }
}