package com.doskoch.legacy.kotlin

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Converts the passed value to hash, using the specified algorithm.
 * @throws NoSuchAlgorithmException if there is no implementation for the specified algorithm.
 */
@Throws(NoSuchAlgorithmException::class)
fun String.toHash(algorithm: String): String {
    val digest = MessageDigest.getInstance(algorithm).apply { update(this@toHash.toByteArray()) }

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