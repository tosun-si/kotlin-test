package com.test

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

/**
 * Tests for Validator class.
 */
class ValidatorTest {

    @Test
    fun `given object with incorrect field values _ when calls validator _ then result has two errors`() {

        // Given.
        val person = Person("Neymar", "", 19)

        // When.
        val errors = Validator.of(person)
                .validate(Person::firstName, Objects::nonNull, "The first name should not be null !")
                .validate(Person::lastName, String::isNotEmpty, "The last name should not be null !")
                .validate(Person::age, Objects::nonNull, "The age should not be null !")
                .validate(Person::age, ::ageGreaterThanTwenty, "The age should be greater than 20 !")
                .getErrors()

        // Then.
        assertThat(errors)
                .isNotNull
                .isNotEmpty
                .hasSize(2)
                .contains("The last name should not be null !")
                .contains("The age should be greater than 20 !")
    }
}

private fun ageGreaterThanTwenty(age: Int): Boolean {
    return age > 20
}