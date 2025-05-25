package com.example.feature_products

import com.example.core_model.domain.Product
import com.example.feature_products.domain.ProductsInteractorImpl
import com.example.feature_products.domain.ProductsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsInteractorImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: ProductsRepository = mockk()
    private lateinit var interactor: ProductsInteractorImpl

    @Before
    fun setUp() {
        interactor = ProductsInteractorImpl(repository)
    }

    @Test
    fun `getProducts should return list from repository`() = runTest(testDispatcher) {
        val expected = listOf(
            Product(
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
        coEvery { repository.getProducts() } returns expected

        val result = interactor.getProducts()

        assertEquals(expected, result)
    }

    @Test
    fun `updateProductViewCount should delegate to repository`() = runTest(testDispatcher) {
        val guid = "123"
        val count = 10

        coEvery { repository.updateProductViewCount(guid, count) } returns Unit

        interactor.updateProductViewCount(guid, count)

        coVerify(exactly = 1) { repository.updateProductViewCount(guid, count) }
    }

    @Test
    fun `updateProductInCartCount should delegate to repository`() = runTest(testDispatcher) {
        val guid = "456"
        val count = 5

        coEvery { repository.updateProductInCartCount(guid, count) } returns Unit

        interactor.updateProductInCartCount(guid, count)

        coVerify(exactly = 1) { repository.updateProductInCartCount(guid, count) }
    }
}
