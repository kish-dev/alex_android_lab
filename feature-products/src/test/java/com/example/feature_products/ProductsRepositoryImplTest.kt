package com.example.feature_products

import com.example.core_db_api.Db
import com.example.core_model.data.db.ProductDbModel
import com.example.core_model.data.db.toProduct
import com.example.core_utils.di.DispatcherProvider
import com.example.core_worker_api.ProductsWorker
import com.example.feature_products.data.ProductsRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsRepositoryImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val dispatcherProvider = TestDispatcherProvider(testDispatcher) as DispatcherProvider

    private val db: Db = mockk(relaxed = true)
    private val worker: ProductsWorker = mockk()
    private lateinit var repository: ProductsRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = ProductsRepositoryImpl(db, worker, dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProducts should return mapped list from worker`() = runTest(testDispatcher) {
        val dbList = listOf(
            ProductDbModel(
                "1",
                "Image",
                "Name",
                "0",
                5.0,
                true,
                true,
                30,
                100
            )
        )
        coEvery { worker.getProductsList() } returns dbList

        val result = repository.getProducts()

        assertEquals(dbList.map { it.toProduct() }, result)
    }

    @Test(expected = RuntimeException::class)
    fun `getProducts should throw if worker fails`() = runTest(testDispatcher) {
        coEvery { worker.getProductsList() } throws RuntimeException("Network error")

        repository.getProducts() // ← упадёт здесь
    }

    @Test
    fun `updateProductViewCount should call db with correct params`() = runTest(testDispatcher) {
        val guid = "test-guid"
        val viewCount = 5

        repository.updateProductViewCount(guid, viewCount)
        advanceUntilIdle()

        coVerify { db.updateProductViewCount(guid, viewCount) }
    }

    @Test
    fun `updateProductViewCount should handle multiple calls`() = runTest(testDispatcher) {
        repeat(3) {
            repository.updateProductViewCount("guid$it", it)
        }
        advanceUntilIdle()
        coVerify(exactly = 3) { db.updateProductViewCount(any(), any()) }
    }

    @Test
    fun `updateProductInCartCount should call db with correct params`() = runTest(testDispatcher) {
        val guid = "test-guid"
        val inCartCount = 3

        repository.updateProductInCartCount(guid, inCartCount)
        advanceUntilIdle()

        coVerify { db.updateProductInCartCount(guid, inCartCount) }
    }

    @Test
    fun `updateProductInCartCount should throw when db fails`() = runTest(testDispatcher) {
        coEvery { db.updateProductInCartCount(any(), any()) } throws RuntimeException("DB error")

        try {
            repository.updateProductInCartCount("guid", 10)
            fail("Expected exception was not thrown")
        } catch (e: RuntimeException) {
            assertEquals("DB error", e.message)
        }
    }
}