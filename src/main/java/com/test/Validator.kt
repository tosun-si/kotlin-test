package com.test

import java.util.*
import kotlin.collections.ArrayList

/**
 * An example of functional validator with Kotlin.
 */
class Validator<T>(val genericObject: T) {

    private val errors: MutableList<String> = ArrayList()

    companion object ValidatorObject {
        fun <T> of(genericObject: T): Validator<T> = Validator(genericObject!!)
    }

    /**
     * Validates a field with the composition of 2 functions.
     * If the predicate on field returns false, the given message is added in error messages stack.
     */
    fun <R> validate(projection: (T) -> R, predicate: (R) -> Boolean, message: String): Validator<T> {

        val predicateOnField: Boolean = predicate(projection(genericObject))

        Optional.of(predicateOnField)
                .filter({ res -> !res })
                .ifPresent({ _ -> errors.add(message) })

        return this
    }

    /**
     * Returns all error messages.
     */
    fun getErrors(): List<String> {
        return errors
    }
}