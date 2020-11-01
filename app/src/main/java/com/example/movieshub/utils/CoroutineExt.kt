package com.example.movieshub.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun launchSilent(
    context: CoroutineContext = Dispatchers.Main,
    job: Job,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
) {
    val coroutineScope = CoroutineScope(context + job)
    coroutineScope.launch(context, start, block)
}

fun <T> runBlockingSilent(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T) {
    runBlocking(context, block)
}