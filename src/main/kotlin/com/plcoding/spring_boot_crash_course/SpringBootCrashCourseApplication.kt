package com.plcoding.spring_boot_crash_course

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["com.plcoding.spring_boot_crash_course.controllers"])
@SpringBootApplication
class SpringBootCrashCourseApplication

fun main(args: Array<String>) {
    runApplication<SpringBootCrashCourseApplication>(*args)
}
