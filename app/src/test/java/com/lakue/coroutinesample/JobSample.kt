package com.lakue.coroutinesample

import kotlinx.coroutines.*
import org.junit.Test

class JobSample {
    val Tag = JobSample::class.java.name

    suspend fun doOneTwoThree() = coroutineScope {
        val job = launch {
            println("launch1: ${Thread.currentThread().name}")
            delay(1000L) //suspension point
            println("3!")
        }
        job.join() //su
        launch {
            println("launch2: ${Thread.currentThread().name}")
            println("1!")
        }
        //10만번 반복한다는 의미
        //repeat와 100_000이 신기해서 써봄
        repeat(100_000){
            launch {
//                println("launch3: ${Thread.currentThread().name}")
                delay(500L) //suspension point
//                println("2!")
            }
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