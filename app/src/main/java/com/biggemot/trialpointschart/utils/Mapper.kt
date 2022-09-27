package com.biggemot.trialpointschart.utils

interface Mapper<InputType, out OutputType> {
    fun map(input: InputType): OutputType
}