package com.extensions.kotlin.functions

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

const val ELLIPSIS = "…"
const val MD5 = "MD5"
const val SHA256 = "SHA-256"

/**
 * Removes any leading and trailing whitespaces and leaves only one whitespace between words.
 * @return [String] without redundant whitespaces.
 */
fun String.removeRedundantSpaces(): String {
    return this
        .trim()
        .replace("\\s{2,}".toRegex(), " ")
}

/**
 * Adds [ELLIPSIS] symbol to the [String].
 * @param [atStart] whether should be added to the beginning of the [String].
 * False is used by default.
 * @param [atEnd] whether should be added to the end of the [String]. True by default.
 */
fun String.withEllipsis(atStart: Boolean = false, atEnd: Boolean = true): String {
    return "${if (atStart) ELLIPSIS else ""}$this${if (atEnd) ELLIPSIS else ""}"
}

/**
 * Converts passed value to hash, using specified algorithm.
 * @throws NoSuchAlgorithmException if there is no implementation for the specified algorithm.
 */
@Throws(NoSuchAlgorithmException::class)
fun String?.toHash(algorithm: String): String {
    val digest = MessageDigest.getInstance(algorithm).apply { update(this@toHash?.toByteArray()) }

    val messageDigest = digest.digest()

    val hexString = StringBuilder()

    messageDigest.forEach {
        val hex = Integer.toHexString(0xFF and it.toInt())

        if (hex.length < 2) {
            hexString.append("0$hex")
        } else {
            hexString.append(hex)
        }
    }

    return hexString.toString()
}