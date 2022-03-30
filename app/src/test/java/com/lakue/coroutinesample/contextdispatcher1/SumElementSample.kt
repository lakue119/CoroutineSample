package com.lakue.coroutinesample.contextdispatcher1

import kotlinx.coroutines.*
import org.junit.Test

@ExperimentalStdlibApi
class SumElementSample {
    @Test
    fun testMain() = runBlocking<Unit> {
        launch {
            launch(Dispatchers.IO + CoroutineName("launch1")) { //부모의 코루틴 콘텍스트 + CoroutineContext
                println("launch1 / ${Thread.currentThread().name}")
                println(coroutineContext[CoroutineDispatcher])
                println(coroutineContext[CoroutineName])
                delay(500L)
            }
            launch(Dispatchers.Default + CoroutineName("launch2")) { //부모의 코루틴 콘텍스트 + CoroutineContext
                println("launch2 / ${Thread.currentThread().name}")
                println(coroutineContext[CoroutineDispatcher])
                println(coroutineContext[CoroutineName])
                delay(10L)
            }
        }
    }
}