package com.lakue.coroutinesample

import kotlinx.coroutines.*
import org.junit.Test

class JobSample2 {
    val Tag = JobSample2::class.java.name

    /**
     * cancel을 했는데도 불구하고
     * 작업이 계속 이어짐.
     * 왜일까?? job은 연속된 계산작업인 경우에는 취소시키지 않습니다. (https://tourspace.tistory.com/151)
     * 0.2초 뒤에 작업은 종료가 되었지만, 후에 job은 종료가 되었지만 while문이 계속 수행.
     * isActive를 통해 활동중인지 체크 후 종료
     */
    suspend fun doCount() = coroutineScope {
        val job1 = launch(Dispatchers.Default) {
            var i = 1
            var nextTime = System.currentTimeMillis() + 1000L

            while (i <= 10 && isActive){
                val currentTime = System.currentTimeMillis()
                if(currentTime >= nextTime){
                    println(i)
                    nextTime = currentTime + 1000L
                    i++
                }
            }
        }
        delay(2000L)
        job1.cancel()
        job1.join() // join을 하지 않으면, doCountDone이 중간에 나타나게됨.
        //job1.cancelAndJoin() cancel과 join을 합쳐서 자주사용하기 때문에, 코루틴에서 지원함. 주로 이거 많이사용
        println("doCount Done")
    }

    @Test
    fun testMain() = runBlocking {
        doCount()
    }


    suspend fun doCount1() = coroutineScope {
        val job1 = launch(Dispatchers.Default) {
            var i = 1
            var nextTime = System.currentTimeMillis() + 100L

            while (i <= 10){
                val currentTime = System.currentTimeMillis()
                if(currentTime >= nextTime){
                    println(i)
                    nextTime = currentTime + 100L
                    i++
                }
            }
        }
        delay(200L)
        job1.cancel()
        println("doCount Done")
    }

    @Test
    fun testMain1() = runBlocking {
        doCount()
    }
}