package com.lakue.coroutinesample.ceh

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.ArithmeticException
import kotlin.random.Random

/**
 * 최상위 코루틴이 runBlocking일 경우 ceh를 사용할 수 없음.
 */
class RunblockingCEHSample {
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
    fun testMain() = runBlocking<Unit> { //1 // 최상단 코루틴
        val job = launch(ceh) { //2
            val a = async { printRandom1() } //3
            val b = async { printRandom2() } //3
            println(a.await())
            println(b.await())
        }
        job.join()
    }
}