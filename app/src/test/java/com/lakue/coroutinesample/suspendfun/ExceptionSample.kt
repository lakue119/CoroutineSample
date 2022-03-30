package com.lakue.coroutinesample.suspendfun

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.IllegalStateException
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 * 코드를 수행하다 보면 예외가 발생할 경우
 * 예외가 발생하면 위쪽의 코루틴 스코프와 아래쪽의 코루틴 스코프가 취소됨
 *
 * 트렌젝션처럼 구조적으로 어떤 작업들을 묶어서 캔슬하거나 예외처리 할 수 있음
 */
class ExceptionSample {
    suspend fun getRandom1(): Int {
        try {
            delay(1000L)
            return Random.nextInt(0, 500)
        } finally {
            println("getRandom1 is cancelled!")
        }
    }

    suspend fun getRandom2(): Int {
        delay(10000L)
        throw IllegalStateException()
    }

    suspend fun doSomething() = coroutineScope {  //부모코루틴 //캔슬해라
        val value1 = async { //자식 코루틴 // value2에서 문제 발생했으니 캔슬해라.
            getRandom1()
        }
        val value2 = async { //자식 코루틴 // 문제 발생
            getRandom2()
        }
        try {
            println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
        } finally {
            println("doSomething is cancelled.")
        }
    }

    @Test
    fun testMain() = runBlocking {
        try {
            doSomething()
        } catch (e: IllegalStateException) {
            println("doSomething failed: $e")
        }
    }
}