import java.io.File
import java.io.FileInputStream
import java.util.*

object Plugins {

    val android = "com.android.tools.build:gradle:${Versions.android}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    object Versions {

        private val versions = Properties().apply {
            load(FileInputStream(File("buildSrc/plugin.properties")))
        }

        val android: String by versions
        val kotlin: String by versions
    }
}