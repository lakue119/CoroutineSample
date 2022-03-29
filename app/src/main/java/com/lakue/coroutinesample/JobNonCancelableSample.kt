package com.lakue.coroutinesample

import kotlinx.coroutines.*

/**
 * Job의 Cancel을 막아주는 방법
 * withContext(NonCancellable)를 사용해 cancel이 되지 않는 부분을 정의
 */
class JobNonCancelableSample {
    val Tag = JobNonCancelableSample::class.java.name

    suspend fun doOneTwoThree() = coroutineScope {
        val job1 = launch {
            withContext(NonCancellable){
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("3!")
            }
            delay(1000L)
            println("job1: end")
        }
        val job2 = launch {
            withContext(NonCancellable){
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("1!")
            }
            delay(1000L)
            println("job2: end")
        }
        val job3 = launch {
            withContext(NonCancellable){
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("2!")
            }
            delay(1000L)
            println("job3: end")
        }
        delay(800L)
        job1.cancel()
        job2.cancel()
        job3.cancel()
        println("4!")
    }

    fun testMain() = runBlocking {
        doOneTwoThree()
        println("runBlocking: ${Thread.currentThread().name}")
        println("5!")
    }
}