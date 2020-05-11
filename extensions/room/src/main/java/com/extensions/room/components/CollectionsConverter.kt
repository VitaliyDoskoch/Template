package com.extensions.room.components

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * It is a collection of common [TypeConverter]s for basic collections.
 */
object CollectionsConverter {

    const val SEPARATOR = ";\n"

    //region Array

    @JvmStatic
    @TypeConverter
    fun fromBooleanArray(items: Array<Boolean>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toBooleanArray(value: String?): Array<Boolean> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toBoolean).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromByteArray(items: Array<Byte>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toByteArray(value: String?): Array<Byte> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toByte).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromShortArray(items: Array<Short>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toShortArray(value: String?): Array<Short> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toShort).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntArray(items: Array<Int>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toIntArray(value: String?): Array<Int> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toInt).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromLongArray(items: Array<Long>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toLongArray(value: String?): Array<Long> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toLong).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromFloatArray(items: Array<Float>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toFloatArray(value: String?): Array<Float> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toFloat).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromDoubleArray(items: Array<Double>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toDoubleArray(value: String?): Array<Double> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toDouble).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromBigDecimalArray(items: Array<BigDecimal>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toBigDecimalArray(value: String?): Array<BigDecimal> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).map(String::toBigDecimal).toTypedArray()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromStringArray(items: Array<String>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toStringArray(value: String?): Array<String> {
        return if (value.isNullOrEmpty()) {
            arrayOf()
        } else {
            value.split(SEPARATOR).toTypedArray()
        }
    }

    //endregion

    //region List

    @JvmStatic
    @TypeConverter
    fun fromBooleanList(items: List<Boolean>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toBooleanList(value: String?): MutableList<Boolean> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toBoolean).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromByteList(items: List<Byte>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toByteList(value: String?): MutableList<Byte> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toByte).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromShortList(items: List<Short>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toShortList(value: String?): MutableList<Short> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toShort).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntList(items: List<Int>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toIntList(value: String?): MutableList<Int> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toInt).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromLongList(items: List<Long>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toLongList(value: String?): MutableList<Long> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toLong).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromFloatList(items: List<Float>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toFloatList(value: String?): MutableList<Float> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toFloat).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromDoubleList(items: List<Double>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toDoubleList(value: String?): MutableList<Double> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toDouble).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromBigDecimalList(items: List<BigDecimal>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toBigDecimalList(value: String?): MutableList<BigDecimal> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).map(String::toBigDecimal).toMutableList()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromStringList(items: List<String>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toStringList(value: String?): MutableList<String> {
        return if (value.isNullOrEmpty()) {
            mutableListOf()
        } else {
            value.split(SEPARATOR).toMutableList()
        }
    }

    //endregion

    //region Set

    @JvmStatic
    @TypeConverter
    fun fromBooleanSet(items: Set<Boolean>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toBooleanSet(value: String?): MutableSet<Boolean> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toBoolean).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromByteSet(items: Set<Byte>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toByteSet(value: String?): MutableSet<Byte> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toByte).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromShortSet(items: Set<Short>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toShortSet(value: String?): MutableSet<Short> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toShort).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromIntSet(items: Set<Int>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toIntSet(value: String?): MutableSet<Int> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toInt).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromLongSet(items: Set<Long>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toLongSet(value: String?): MutableSet<Long> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toLong).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromFloatSet(items: Set<Float>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toFloatSet(value: String?): MutableSet<Float> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toFloat).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromDoubleSet(items: Set<Double>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toDoubleSet(value: String?): MutableSet<Double> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toDouble).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromBigDecimalSet(items: Set<BigDecimal>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toBigDecimalSet(value: String?): MutableSet<BigDecimal> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).map(String::toBigDecimal).toMutableSet()
        }
    }

    @JvmStatic
    @TypeConverter
    fun fromStringSet(items: Set<String>): String = items.joinToString(SEPARATOR)

    @JvmStatic
    @TypeConverter
    fun toStringSet(value: String?): MutableSet<String> {
        return if (value.isNullOrEmpty()) {
            mutableSetOf()
        } else {
            value.split(SEPARATOR).toMutableSet()
        }
    }

    //endregion
}