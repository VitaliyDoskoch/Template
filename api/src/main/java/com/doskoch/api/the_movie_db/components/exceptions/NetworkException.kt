package com.doskoch.api.the_movie_db.components.exceptions

class NetworkException(
    val url: String,
    val type: Type,
    val reason: Any,
    val description: String? = null,
    cause: Throwable? = null
) : RuntimeException(cause) {

    enum class Type {
        /**
         * non-200 HTTP status code was received from the server
         */
        HTTP,

        /**
         * IOException occurred while communicating to the server
         */
        NETWORK,

        /**
         * internal error occurred while attempting to execute a request
         */
        UNKNOWN,

        /**
         * server response contains error flag or is invalid
         */
        SERVER
    }

    override val message: String
        get() {
            return """
                |
                |url: $url
                |type: ${type.name}
                |reason: $reason
                |description: $description
                |message: ${super.message}
            """.trimMargin()
        }
}
