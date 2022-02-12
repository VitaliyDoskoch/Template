package com.doskoch.apis.the_movie_db.components.validator

import kotlin.reflect.KClass

data class ValidationResult(
    val fields: Pair<KClass<out SelfValidator>, Set<Pair<String, Any>>>,
    val nested: Set<ValidationResult>?
) {

    fun printReport(): String {
        return printReport(this, 0).toString()
    }

    private fun printReport(result: ValidationResult, tabs: Int): StringBuffer {
        val buffer = StringBuffer("")
        val tab = (0 until tabs).joinToString(separator = "") { "\t" }

        if (result.fields.second.isNotEmpty()) {
            buffer.append("$tab${result.fields.first.java.simpleName}:\n")
            buffer.append(
                result.fields.second.joinToString(
                    ";\n\t$tab",
                    "\t$tab",
                    ".\n"
                ) { (name, value) ->
                    "$name: $value"
                })
        }

        result.nested?.forEach {
            buffer.append(printReport(it, tabs + 1))
        }

        return buffer
    }
}