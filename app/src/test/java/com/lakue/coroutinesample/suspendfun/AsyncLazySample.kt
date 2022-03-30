package com.lakue.coroutinesample.suspendfun

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * async 키워드를 사용하는 순간 코드블럭이 수행을 준비하는데
 * async(start = CoroutineStart.LAZY)로 인자를 전달하면 우리가 원하는 순간 수행을 준비하게 할 수 있다
 * 이후 start 메서드를 이용해 수행을 준비하게 할 수 있다
 */
class AsyncLazySample {
    suspend fun getRandom1(): Int{
        delay(1000L)
        return Random.nextInt(0,500)
    }

    suspend fun getRandom2(): Int{
        delay(1000L)
        return Random.nextInt(0,500)
    }

    @Test
    fun testMain() = runBlocking {
        val elapsedTime = measureTimeMillis {
            val value1 = async(start = CoroutineStart.LAZY) {
                getRandom1()
            } // 코루틴이 만들어지지만 수행예약이 되지 않는다.
            val value2 = async(start = CoroutineStart.LAZY) {
                getRandom2()
            }
            value1.start() //큐에 수행 예약을 한다.
            value2.start()

            println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
        }
        println(elapsedTime)
    }
}