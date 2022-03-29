package com.lakue.coroutinesample

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * RunBlocking은 값을 반환할 수 있다.
 * RunBlocking은 블로킹 수법이다
 * 블로킹 : 제어권을 다른 코루틴에 넘기지 않고
 *        자기가 가지고 있다가 완료가 되면 제어권을 넘긴다.
 * 논블로킹 : 제어권을 넘기지 않고 자기가 가지고 있지만
 *          다른 함수를 실행시켜 동시에 실행 가능
 */
class ReturnRunBlocking {
    val Tag = ReturnRunBlocking::class.java.name

    fun getIntItem() = runBlocking<Int> {
        println("runBlocking: ${Thread.currentThread().name}")
        delay(10000L)
        5
    }

    @Test
    fun testMain(){
        println("1")
        println("2")
        println("3")
        println("4")
        println("${getIntItem()}")
        println("6")
    }
}