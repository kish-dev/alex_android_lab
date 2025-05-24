package com.example.feature_products

import com.example.core_utils.di.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher

class TestDispatcherProvider(private val testDispatcher: CoroutineDispatcher) : DispatcherProvider {
    override val io = testDispatcher
    override val main = testDispatcher
}