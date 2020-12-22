@file: Suppress("UNCHECKED_CAST")

package com.iml1s.interview.test.utils

import java.lang.reflect.Field
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@Throws(IllegalAccessException::class, ClassCastException::class)
inline fun <reified T> Any.getField(fieldName: String): T? {
    this::class.memberProperties.forEach { kCallable ->
        if (fieldName == kCallable.name) {
            kCallable.isAccessible = true
//            kCallable.getValue(this)
            return kCallable.getter.call(this) as T?
        }
    }
    return null
}

fun <R> readInstanceProperty(instance: Any, propertyName: String): R {
    val property = instance::class.members
        // don't cast here to <Any, R>, it would succeed silently
        .first { it.name == propertyName } as KProperty1<Any, *>
    property.isAccessible = true
    // force a invalid cast exception if incorrect type here
    return property.get(instance) as R
}

fun <T> readInstanceField(instance: Any, fieldName: String): T {
    val privateStringField: Field = instance::class.java.getDeclaredField(fieldName)
    privateStringField.isAccessible = true
    return privateStringField.get(instance) as T
}
