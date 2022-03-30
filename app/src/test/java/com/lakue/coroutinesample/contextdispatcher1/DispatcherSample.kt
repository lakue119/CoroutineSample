package com.lakue.coroutinesample.contextdispatcher1

import kotlinx.coroutines.*
import org.junit.Test

/**
 * Default와 IO의 차이
 * Default - 코어수보다 훨씬 많은 쓰레드를 만드는데 유리하지 못함. (복잡한 연산에 주로 사용)
 * IO - IO작업을 할 경우, 많으 쓰레드를 만들어도 크게 비용이 들지 않음
 * 정책의  차이임
 *
 * Dispachers는 launch뿐만아니라 async, withContext등 에서도 사용 가능
 *
 */
class DispatcherSample {
    @Test
    fun testMain() = runBlocking<Unit> {
        //Dispacher 설정을 안했을 시 = 부모의 콘텍스트
        launch {
            println("부모의 콘텍스트 / ${Thread.currentThread().name}")
        }
        //Default - 복잡한 일을 처리함
        //코어 수에 비례하는 스레드 풀에서 수행
        launch(Dispatchers.Default) {
            println("Default / ${Thread.currentThread().name}")
        }
        //IO - 파일을 읽을 때 처리
        //코어 수 보다 많은 쓰레드를 가지는 스레드 풀. IO작업은 CPU를 덜 소모하기 때문
        launch(Dispatchers.IO) {
            println("IO / ${Thread.currentThread().name}")
        }
        //Unconfined
        //어디에도 속하지 않음. 지금 시점에서는 부모의 스레드에서 수행
        launch(Dispatchers.Unconfined) {
            println("Unconfined / ${Thread.currentThread().name}")
            delay(100L)
            //잠이 들었다가 깨면, 어디서 수행될 지 모름
            println("Unconfined / ${Thread.currentThread().name}")
        }
        //newSingleThreadContext
        //항상 새로운 스레드를 생성
        launch(newSingleThreadContext("Lakue")) {
            println("newSingleThreadContext / ${Thread.currentThread().name}")
        }
    }
}