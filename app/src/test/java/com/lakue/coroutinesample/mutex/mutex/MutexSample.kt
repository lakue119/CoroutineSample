package com.lakue.coroutinesample.mutex.mutex

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * 뮤텍스는 상호배제(Mutual exclusion)의 줄임말입니다.
 * 공유 상태를 수정할 때 임계 영역(critical section)를 이용하게 하며, 임계 영역을 동시에 접근하는 것을 허용하지 않습니다.
 */
class MutexSample {
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

    val mutex = Mutex()
    var counter = 0

    @Test
    fun testMain() = runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                mutex.withLock {
                    counter++
                }
            }
        }
        println("Counter = $counter")
    }
}