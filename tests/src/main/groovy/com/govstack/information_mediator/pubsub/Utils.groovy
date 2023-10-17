package com.govstack.information_mediator.pubsub

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import io.qameta.allure.Step

import java.util.concurrent.TimeoutException
import java.util.function.BooleanSupplier

class Utils {

    static boolean isRunningInDocker() {
        def cgroupFile = new File('/proc/1/cgroup')
        // Check if the OS is Linux and /proc/1/cgroup exists
        if (System.getProperty('os.name').toLowerCase().contains('linux') && cgroupFile.exists()) {
            // Check if /proc/1/cgroup contents contain the string "docker"
            return cgroupFile.text.contains('docker')
        }
        return false
    }

    static String mapToJsonString(Map map) {
        return new JsonBuilder(map).toString()
    }

    static Object objectToMap(Object object) {
        return new JsonSlurper().parseText(object.toString())
    }

    @Step("Waiting until")
    static boolean waitUntil(BooleanSupplier condition, int timeoutMs = 10_000, int waitTimeMs = 100) {
        long startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() < startTime + timeoutMs) {
            if (condition.getAsBoolean()) {
                println("Condition met in ${System.currentTimeMillis() - startTime} ms")
                return true
            } else {
                Thread.sleep(waitTimeMs)
            }
        }
        throw new TimeoutException("Timed out in $timeoutMs ms")
    }

    static String technicalErrorRegex = "Technical error: ([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"

}
