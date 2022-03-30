package com.lakue.coroutinesample.suspendfun

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * 기존의 susspend함수에서
 * launch를 사용하게 되면 순차적으로 실행이 된다.
 * 예를들어 다음과 같은 예를 launch로 사용하게 되면 순차적으로 실행하게 되, 2초이상을 소모하게 된다.
 *
 * async를 사용하게 되면, 동시에 실행이 된다.
 * await를 통해서 async가 결과를 가져올때까지 기다린 후
 * value를 가져오기 때문에, 두개 모두 실행하면 1초정도를 소모하게 된다.
 *
 * ** launch는 그냥 실행만, async는 실행값을 리턴할 때 사용
 */
class AsyncSample {
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
            val value1 = async {
                getRandom1()
            }
            val value2 = async {
                getRandom2()
            }

            println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
        }
        println(elapsedTime)
    }
}