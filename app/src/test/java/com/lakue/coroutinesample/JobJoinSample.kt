package com.lakue.coroutinesample

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * join은 해당 job이 실행이 끝날 때까지 기다렸다가
 * 다음 동작을 수행
 *
 * 결과 : 3 -> 4 -> 1 -> 2 -> 5
 * 4가 먼저 오는 이유 = 코드블럭은 launch스코프를 순차적으로 대기를 했다가
 * launch 외 블럭을 실행 후 launch를 순차적으로 실행
 */
class JobJoinSample {
    val Tag = JobJoinSample::class.java.name

    suspend fun doOneTwoThree() = coroutineScope {
        val job = launch {
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L) //suspension point
            println("3!")
        }
        job.join() //suspension point
        println("6!")
        launch {
            println("launch2: ${Thread.currentThread().name}")
            println("1!")
        }
        println("7!")
        launch {
            println("launch3: ${Thread.currentThread().name}")
            delay(500L) //suspension point
            println("2!")
        }
        println("4!")
    }

    @Test
    fun testMain() = runBlocking {
        doOneTwoThree()
        println("runBlocking: ${Thread.currentThread().name}")
        println("5!")
    }
}