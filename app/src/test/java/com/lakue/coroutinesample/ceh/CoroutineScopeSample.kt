package com.lakue.coroutinesample.ceh

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.random.Random

/**
 * CoroutineScope
 * GlobalScope보다 권장되는 형식.
 * 인자로 CORoutineContext를 받는데 코루틴 엘리먼트를 하나만 넣어도 좋고, 엘리먼트를 합쳐도 된다.
 *
 * scope를 계층적으로 관리할 수 있다.
 *
 */
class CoroutineScopeSample {
    suspend fun printRandom(){
        delay(500L)
        println(Random.nextInt(0,500))
    }

    @Test
    fun testMain(){
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch(Dispatchers.IO) {
            launch { printRandom() }
        }
        Thread.sleep(1000L) //runBlocking이 아니기 때문에 Sleep을 사용
    }
}