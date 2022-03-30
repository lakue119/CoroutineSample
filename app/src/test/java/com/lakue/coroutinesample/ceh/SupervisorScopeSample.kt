package com.lakue.coroutinesample.ceh

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.ArithmeticException
import kotlin.random.Random

/**
 *
 */
class SupervisorScopeSample {
    suspend fun printRandom1(){
        delay(500L)
        println(Random.nextInt(0,500))
    }
    suspend fun printRandom2(){
        delay(500L)
        throw ArithmeticException()
    }
    suspend fun supervisoredFunc() = supervisorScope {
        launch { printRandom1() }
        launch(ceh) { printRandom2() } //무조건 예외가 발생한 곳에 CEH를 넣어줘야한다.
    }

    val ceh = CoroutineExceptionHandler{ _, exception ->
        println("Something happen: $exception")
    }

    @Test
    fun testMain() = runBlocking {
        val scope = CoroutineScope(Dispatchers.IO)
        val job = scope.launch {
            supervisoredFunc()
        }
        job.join()
    }
}