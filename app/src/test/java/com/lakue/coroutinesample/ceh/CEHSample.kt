package com.lakue.coroutinesample.ceh

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.ArithmeticException
import kotlin.random.Random

/**
 * CEH(CoroutineExceptionHandler)
 * 코루틴 내에서 에러가 났을 경우 처리를 할 수 있다.
 * 기본적으로 Try catch를 이용하는데, CEH를 이용해 한번에 처리가능
 */
class CEHSample {
    suspend fun printRandom1(){
        delay(500L)
        println(Random.nextInt(0,500))
    }
    suspend fun printRandom2(){
        delay(500L)
        throw ArithmeticException()
    }

    val ceh = CoroutineExceptionHandler{ _, exception ->
        println("Something happen: $exception")
    }

    @Test
    fun testMain() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch(ceh) {
            launch { printRandom1() }
            launch { printRandom2() }
        }
        job.join()
    }
}