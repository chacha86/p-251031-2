package com.back

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
open class BackApplication // Configuration 클래스가 proxy 처리 되므로 final 클래스일 수 없다. open class 여야 한다.

fun main(args: Array<String>) {
    runApplication<BackApplication>(*args)
}
