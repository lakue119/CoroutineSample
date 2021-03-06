package com.lakue.coroutinesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    suspend fun doOneTwoThree() = coroutineScope {
        val job1 = launch {
            withContext(NonCancellable){
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("3!")
            }
            delay(1000L)
            println("job1: end")
        }
        val job2 = launch {
            withContext(NonCancellable){
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("1!")
            }
            delay(1000L)
            println("job2: end")
        }
        val job3 = launch {
            withContext(NonCancellable){
                println("launch1: ${Thread.currentThread().name}")
                delay(1000L)
                println("2!")
            }
            delay(1000L)
            println("job3: end")
        }
        delay(800L)
        job1.cancel()
        job2.cancel()
        job3.cancel()
        println("4!")
    }

    fun testMain() = runBlocking {
        doOneTwoThree()
        println("runBlocking: ${Thread.currentThread().name}")
        println("5!")
    }
}