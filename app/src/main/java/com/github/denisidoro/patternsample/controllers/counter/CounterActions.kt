package com.github.denisidoro.patternsample.controllers.counter

sealed class CounterActions {
    object INCREMENT
    object DECREMENT
}