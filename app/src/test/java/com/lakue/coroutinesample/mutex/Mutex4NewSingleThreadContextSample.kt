package com.lakue.coroutinesample.mutex

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * newSingleThreadContext를 이용해서 특정한 스레드를 만들고 해당 스레드를 사용할 수 있습니다.
 */
class Mutex4NewSingleThreadContextSample {
    suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100 // 시작할 코루틴의 갯수
        val k = 1000 // 코루틴 내에서 반복할 횟수
        val elapsed = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        println("$elapsed ms동안 ${n * k}개의 액션을 수행했습니다.")
    }

    var counter = 0
    val counterContext = newSingleThreadContext("CounterContext")

    @Test
    fun testMain() = runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
            }
        }
        println("Counter = $counter")
    }
}