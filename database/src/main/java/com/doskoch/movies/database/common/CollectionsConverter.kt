package com.doskoch.movies.database.common

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * It is a collection of common [TypeConverter]s for basic collections.
 */
@Suppress("MemberVisibilityCanBePrivate")
object CollectionsConverter {

    const val PREFIX = "{\n\""
    const val SEPARATOR = "\",\n\""
    const val POSTFIX = "\"\n}"
    const val STRING_SEPARATOR = "[END_OF_STRING]\";\n\""

    //region Array

    @JvmStatic
    @TypeConverter
    fun fromBooleanArray(items: Array<Boolean>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toBooleanArray(value: String?): Array<Boolean> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toBoolean)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromByteArray(items: Array<Byte>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toByteArray(value: String?): Array<Byte> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toByte)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromShortArray(items: Array<Short>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toShortArray(value: String?): Array<Short> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toShort)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromIntArray(items: Array<Int>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toIntArray(value: String?): Array<Int> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toInt)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromLongArray(items: Array<Long>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toLongArray(value: String?): Array<Long> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toLong)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromFloatArray(items: Array<Float>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toFloatArray(value: String?): Array<Float> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toFloat)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromDoubleArray(items: Array<Double>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toDoubleArray(value: String?): Array<Double> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toDouble)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromBigDecimalArray(items: Array<BigDecimal>): String = items.joinToString(SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toBigDecimalArray(value: String?): Array<BigDecimal> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(SEPARATOR)
        .map(String::toBigDecimal)
        .toTypedArray()

    @JvmStatic
    @TypeConverter
    fun fromStringArray(items: Array<String>): String = items.joinToString(STRING_SEPARATOR, PREFIX, POSTFIX)

    @JvmStatic
    @TypeConverter
    fun toStringArray(value: String?): Array<String> = value.orEmpty()
        .removeSurrounding(PREFIX, POSTFIX)
        .split(STRING_SEPARATOR)
        .toTypedArray()

    //endregion

    //region List

    @JvmStatic
    @TypeConverter
    fun fromBooleanList(items: List<Boolean>): String = fromBooleanArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toBooleanList(value: String?): List<Boolean> = toBooleanArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromByteList(items: List<Byte>): String = fromByteArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toByteList(value: String?): List<Byte> = toByteArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromShortList(items: List<Short>): String = fromShortArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toShortList(value: String?): List<Short> = toShortArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromIntList(items: List<Int>): String = fromIntArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toIntList(value: String?): List<Int> = toIntArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromLongList(items: List<Long>): String = fromLongArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toLongList(value: String?): List<Long> = toLongArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromFloatList(items: List<Float>): String = fromFloatArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toFloatList(value: String?): List<Float> = toFloatArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromDoubleList(items: List<Double>): String = fromDoubleArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toDoubleList(value: String?): List<Double> = toDoubleArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromBigDecimalList(items: List<BigDecimal>): String = fromBigDecimalArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toBigDecimalList(value: String?): List<BigDecimal> = toBigDecimalArray(value).toList()

    @JvmStatic
    @TypeConverter
    fun fromStringList(items: List<String>): String = fromStringArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toStringList(value: String?): List<String> = toStringArray(value).toList()

    //endregion

    //region Set

    @JvmStatic
    @TypeConverter
    fun fromBooleanSet(items: Set<Boolean>): String = fromBooleanArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toBooleanSet(value: String?): Set<Boolean> = toBooleanArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromByteSet(items: Set<Byte>): String = fromByteArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toByteSet(value: String?): Set<Byte> = toByteArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromShortSet(items: Set<Short>): String = fromShortArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toShortSet(value: String?): Set<Short> = toShortArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromIntSet(items: Set<Int>): String = fromIntArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toIntSet(value: String?): Set<Int> = toIntArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromLongSet(items: Set<Long>): String = fromLongArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toLongSet(value: String?): Set<Long> = toLongArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromFloatSet(items: Set<Float>): String = fromFloatArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toFloatSet(value: String?): Set<Float> = toFloatArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromDoubleSet(items: Set<Double>): String = fromDoubleArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toDoubleSet(value: String?): Set<Double> = toDoubleArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromBigDecimalSet(items: Set<BigDecimal>): String = fromBigDecimalArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toBigDecimalSet(value: String?): Set<BigDecimal> = toBigDecimalArray(value).toSet()

    @JvmStatic
    @TypeConverter
    fun fromStringSet(items: Set<String>): String = fromStringArray(items.toTypedArray())

    @JvmStatic
    @TypeConverter
    fun toStringSet(value: String?): Set<String> = toStringArray(value).toSet()

    //endregion
}
