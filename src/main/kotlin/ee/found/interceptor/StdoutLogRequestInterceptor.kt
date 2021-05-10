package ee.found.interceptor

import com.github.kittinunf.fuel.core.FoldableRequestInterceptor
import com.github.kittinunf.fuel.core.RequestTransformer
import ee.found.helper.lazyLogger

object StdoutLogRequestInterceptor : FoldableRequestInterceptor {
    private val logger by lazyLogger()

    override fun invoke(next: RequestTransformer): RequestTransformer {
        return { request ->
            val headers = "\nHeaders: \n${request.headers}"
            val body = if (request.body.isEmpty()) "" else "\nBody:\n${String(request.body.toByteArray())}"

            logger.info { " --> ${request.method} ${request.url}" }
            logger.debug { "$headers$body" }

            next(request)
        }
    }
}
