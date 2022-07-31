package com.doskoch.legacy.android.functions

import android.os.Build
import android.util.Log

/**
 * Creates the build info report, that does not require any permission. See [Build].
 * @return [String] with build info.
 */
fun buildInfoReport(): String {
    return StringBuffer()
        .append("SDK_INT: ${Build.VERSION.SDK_INT};\n")
        .append("CODENAME: ${Build.VERSION.CODENAME};\n")
        .append("RELEASE: ${Build.VERSION.RELEASE};\n")
        .append("BOARD: ${Build.BOARD};\n")
        .append("BOOTLOADER: ${Build.BOOTLOADER};\n")
        .append("BRAND: ${Build.BRAND};\n")
        .append("DEVICE: ${Build.DEVICE};\n")
        .append("DISPLAY: ${Build.DISPLAY};\n")
        .append("FINGERPRINT: ${Build.FINGERPRINT};\n")
        .append("HARDWARE: ${Build.HARDWARE};\n")
        .append("HOST: ${Build.HOST};\n")
        .append("ID: ${Build.ID};\n")
        .append("MANUFACTURER: ${Build.MANUFACTURER};\n")
        .append("MODEL: ${Build.MODEL};\n")
        .append("PRODUCT: ${Build.PRODUCT};\n")
        .append("SUPPORTED_32_BIT_ABIS: ${Build.SUPPORTED_32_BIT_ABIS.joinToString()};\n")
        .append("SUPPORTED_64_BIT_ABIS: ${Build.SUPPORTED_64_BIT_ABIS.joinToString()};\n")
        .append("SUPPORTED_ABIS: ${Build.SUPPORTED_ABIS.joinToString()};\n")
        .append("TAGS: ${Build.TAGS};\n")
        .append("TIME: ${Build.TIME};\n")
        .append("TYPE: ${Build.TYPE};\n")
        .append("USER: ${Build.USER}.")
        .toString()
}