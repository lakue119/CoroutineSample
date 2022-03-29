package com.lakue.coroutinesample

import kotlinx.coroutines.*
import org.junit.Test

/**
 * 일정 시간이 끝난 후에 종료하고 싶다면
 * withTimeout을 사용 (notnull같은 존재)
 * null이 리턴되야하는 경우 TimeoutCancellationException에러가 발생
 *
 * withTimeoutNull을 이용하면 타임아웃 될 때
 * null을 반환할 수 있음.
 */
class withTimeoutSample {
    val Tag = withTimeoutSample::class.java.name

    suspend fun doCount() = coroutineScope {
        val job1 = launch(Dispatchers.Default) {
            var i = 1
            var nextTime = System.currentTimeMillis() + 100L

            while (i <= 10 && isActive){
                val currentTime = System.currentTimeMillis()
                if(currentTime >= nextTime){
                    println(i)
                    nextTime = currentTime + 100L
                    i++
                }
            }
        }

    }

    @Test
    fun textMain() = runBlocking {
        val result = withTimeout(500L) {
            doCount()
            true
        } ?: false
        println(result)
    }
}