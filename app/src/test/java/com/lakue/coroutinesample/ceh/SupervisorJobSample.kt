package com.lakue.coroutinesample.ceh

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.ArithmeticException
import kotlin.random.Random

/**
 * 일반적인 JOB
 * - 예외가 발생하면 cancel을 윗방향, 아랫방향 둘다 하게됨.
 * - 자식이 문제생기면 부모도 cancel, 자식의 자식이 있으면 모두 cancel됨.
 *
 * SupervisorJob
 * 슈퍼바이저 잡은 예외에 의한 취소를 아래쪽으로 내려가게 한다.
 * (job root : 자식1에서 에러 -> 부모 cancel -> 부모의자식들 모두 cancel
 * (supervisorjob root : 자식1에서 에러 -> 끝
 */
class SupervisorJobSample {
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
    fun testMain() = runBlocking {
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob() + ceh)
        val job1 = scope.launch { printRandom1() }
        val job2 = scope.launch { printRandom2() }
        joinAll(job1, job2)
    }
}