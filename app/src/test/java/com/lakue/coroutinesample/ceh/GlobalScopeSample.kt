package com.lakue.coroutinesample.ceh

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test
import kotlin.random.Random

/**
 * GlobalScope
 * 어디에도 속하지 않지만 원래부터 존재하는 전역 Scope
 *
 * 장점 : 간편하게 사용할 수 있다.
 * 단점 : 어떤 계층에도 속하지 않고, 영원히 동작하게 된다는 문제점이 있다.
 *      관리하기가 힘들어 잘 사용하지 않는다.
 */
class GlobalScopeSample {
    suspend fun printRandom(){
        delay(500L)
        println(Random.nextInt(0,500))
    }

    @Test
    fun testMain(){
        val job = GlobalScope.launch(Dispatchers.IO) {
            launch { printRandom() }
        }
        Thread.sleep(1000L) //runBlocking이 아니기 때문에 Sleep을 사용
    }
}