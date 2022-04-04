package com.lakue.coroutinesample.mutex

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * volatile은 가시성 문제만을 해결할 뿐 동시에 읽고 수정해서 생기는 문제를 해결하지 못합니다.
 */
class Mutex2VolatileSample {
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

    /**
     * Volatile
     * - 가시성을 제공해주는 어노테이션
     * - 어떤 쓰레드에서 변경을 해도 다른 쓰레드에 영향을 줌
     *
     */
    @Volatile
    var counter = 0

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