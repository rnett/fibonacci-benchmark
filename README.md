
To run benchmarks: `gradlew runAll -q`.

<hr/>

My results (JVM 15, Node.js 15.8.0, Windows):
```
NodeJS: Recursive:  88.7ms  Tailrec 2.28us
JVM:    Recursive:  31.1ms  Tailrec 19.9us
Native: Recursive:  37.5ms  Tailrec 398ns
```

<hr/>

Code: [Bench.kt](src/commonMain/kotlin/com/rnett/benchmark/Bench.kt):
```kotlin
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
```