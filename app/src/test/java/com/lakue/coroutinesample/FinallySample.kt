package com.lakue.coroutinesample

import kotlinx.coroutines.*
import org.junit.Test

/**
 * Job을 Cancel해도 꼭 마무리로 사용해야 하는 코드가 있다면
 * finally를 사용
 */
class FinallySample {
    val Tag = FinallySample::class.java.name

    suspend fun doOneTwoThree() = coroutineScope {
        val job1 = launch {
            try {
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("3!")
            } finally {
                println("job1 is finishing!")
                //ex.파일을 닫아주는 코드
            }
        }
        val job2 = launch {
            try {
                println("launch2: ${Thread.currentThread().name}")
                delay(1000L)
                println("1!")
            } finally {
                println("job2 is finishing!")
                //ex.소켓을 닫아주는 코드
            }
        }
        val job3 = launch {
            try {
                println("launch3: ${Thread.currentThread().name}")
                delay(1000L)
                println("2!")
            } finally {
                println("job3 is finishing!")
            }
        }
        delay(800L)
        job1.cancel()
        job2.cancel()
        job3.cancel()
        println("4!")
    }

    @Test
    fun testMain() = runBlocking {
        doOneTwoThree()
        println("runBlocking: ${Thread.currentThread().name}")
        println("5!")
    }
}