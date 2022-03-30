package com.lakue.coroutinesample.contextdispatcher1

import kotlinx.coroutines.*
import org.junit.Test

class ParentJobSample {
    @Test
    fun testMain() = runBlocking<Unit> { //증부모
        val job = launch { // 부모
            //원래대로라면 0.5초의 딜레이를 가지고, 취소를 시켰다면 3이 출려되면 안된다.
            //하지만 Job()을 넣어줌으로써 해당 launch는 부모 코루틴과의 관계가 끊어져 취소가 되지않고 실행이 된다.
            launch(Job()) {
                println(coroutineContext[Job])
                println("launch1 / ${Thread.currentThread().name}")
                delay(1000L)
                println("3!")
            }
            launch {
                println(coroutineContext[Job])
                println("launch2 / ${Thread.currentThread().name}")
                delay(1000L)
                println("1!")
            }
        }

        delay(500L)
        job.cancelAndJoin()
        delay(1000L)
    }
}