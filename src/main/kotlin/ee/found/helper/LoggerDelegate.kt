package ee.found.helper

import mu.KLogger
import mu.KotlinLogging
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

// unwrap companion class to enclosing class given a Java Class
fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass
}

// unwrap companion class to enclosing class given a Kotlin Class
fun <T : Any> unwrapCompanionClass(ofClass: KClass<T>): KClass<*> {
    return unwrapCompanionClass(ofClass.java).kotlin
}

// Return logger for Kotlin class
fun <T : Any> logger(forClass: KClass<T>): KLogger {
    return KotlinLogging.logger(unwrapCompanionClass(forClass).qualifiedName!!)
}

// return logger from extended class (or the enclosing class)
fun <T : Any> T.logger(): KLogger {
    return logger(this::class)
}

// return a lazy logger property delegate for enclosing class
fun <R : Any> R.lazyLogger(): Lazy<KLogger> {
    val log = logger(this::class)
    return lazy { log }
}

// return a logger property delegate for enclosing class
fun <R : Any> R.injectLogger(): Lazy<KLogger> {
    return lazyOf(logger(this::class))
}
