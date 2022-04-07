package com.lakue.coroutinesample.mutex.mutex

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import org.junit.Test
import kotlin.system.measureTimeMillis

/**
 * SealdClass
 * 외부에서 확장이 불가능한 클래스
 */
sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

class ActorSample {
    suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100
        val k = 1000
        val elapsed = measureTimeMillis {
            coroutineScope {
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        println("$elapsed ms동안 ${n * k}개의 액션을 수행했습니다.")
    }

    sealed class CounterMsg
    object IncCounter : CounterMsg()
    class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

    fun CoroutineScope.counterActor() = actor<CounterMsg> {
        var counter = 0 // 액터 안에 상태를 캡슐화해두고 다른 코루틴이 접근하지 못하게 합니다.
        for (msg in channel) { // 외부에서 보내는 것은 채널을 통해서만 받을 수 있습니다.(recieve)
            when (msg) {
                is IncCounter -> counter++ // 증가시키는 신호.
                is GetCounter -> msg.response.complete(counter) // 현재 상태를 반환합니다.
            }
        }
    }

    @Test
    fun testMain() = runBlocking<Unit> {
        val counter = counterActor()
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.send(IncCounter)
            }
        }

        val response = CompletableDeferred<Int>()
        counter.send(GetCounter(response))
        println("Counter = ${response.await()}")
        counter.close()
    }
}