package com.lakue.coroutinesample.mutex

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * AtomicInteger가 이 문제에는 적합한데 항상 정답은 아닙니다.
 */
class Mutex3AtomicIntegerSample {
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

    var counter = AtomicInteger()

    @Test
    fun testMain() = runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                //값을 증가시키고, 현재 가지고 있는 값을 리턴, 그 과정에서 다른 쓰레드가 값을 변경할 수 없음.
                counter.incrementAndGet()
            }
        }
        println("Counter = $counter")
    }
}