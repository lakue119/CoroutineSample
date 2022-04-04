package com.lakue.coroutinesample.mutex

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * 여러 쓰레드가 데이터 공유가 되지 않아서 Counter이 원하는 값이 나오지 않는다.
 *
 * withContext는 수행이 완료될 때 까지 기다리는 코루틴 빌더입니다.
 * 뒤의 println("Counter = $counter") 부분은 잠이 들었다
 * withContext 블록의 코드가 모두 수행되면 깨어나 호출됩니다.
 * 위의 코드는 불행히도 항상 10000이 되는 것은 아닙니다.
 * Dispatchers.Default에 의해 코루틴이 어떻게 할당되냐에 따라 값이 달라집니다.
 */
class Mutex1Sample {
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