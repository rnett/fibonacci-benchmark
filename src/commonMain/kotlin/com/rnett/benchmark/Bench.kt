package com.rnett.benchmark

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun fib(n: Int): Int {
    if (n <= 1)
        return 0
    return fib(n - 1) + fib(n - 2)
}

tailrec fun fibTailrec(index: Int, prev: Int = 1, current: Int = 0): Int {
    if (index == 0)
        return current

    if (index == 1)
        return prev

    return fibTailrec(index - 1, prev + current, prev)
}

inline fun measure(times: Int = 100, block: () -> Unit): Duration {
    val measurements = List(times) {
        measureTime {
            block()
        }
    }
    return measurements.reduce { a, b -> a + b } / times
}

fun benchmark(name: String) {
    val fib = measure {
        fib(35)
    }

    val fibInline = measure {
        fibTailrec(35)
    }

    println("$name:\tRecursive:\t$fib\tTailrec\t$fibInline")
}