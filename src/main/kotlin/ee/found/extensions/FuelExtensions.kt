package ee.found.extensions

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import ee.found.interceptor.AllureRequestInterceptor
import ee.found.interceptor.AllureResponseInterceptor
import ee.found.interceptor.StdoutLogRequestInterceptor
import ee.found.interceptor.StdoutLogResponseInterceptor

fun Headers.pretty(): String {
    var result = ""
    this.forEach { key, value ->
        result += "$key : ${value}\n"
    }
    return result
}

fun fuelManager() = FuelManager()
    .addRequestInterceptor(AllureRequestInterceptor)
    .addRequestInterceptor(StdoutLogRequestInterceptor)
    .addResponseInterceptor(AllureResponseInterceptor)
    .addResponseInterceptor(StdoutLogResponseInterceptor)
