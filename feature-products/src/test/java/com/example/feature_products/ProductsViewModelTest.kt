package com.example.feature_products

import com.example.core_model.domain.Product
import com.example.core_model.domain.ProductState
import com.example.core_model.domain.toVO
import com.example.core_model.presentation.ProductInListVO
import com.example.core_utils.di.DispatcherProvider
import com.example.feature_products.domain.ProductsInteractor
import com.example.feature_products.presentation.viewModel.ProductsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProvider(testDispatcher)
    private val interactor: ProductsInteractor = mockk()

    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProductsViewModel(interactor, dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getProducts should emit Loading then Loaded`() = runTest(testDispatcher) {
        val domainProducts = listOf(
            Product(
                guid = "1",
                image = "Image",
                name = "Name",
                price = "0",
                rating = 5.0,
                isFavorite = true,
                viewCount = 30,
                inCartCount = 100,
                isInCart = true
            )
        )

        coEvery { interactor.getProducts() } returns domainProducts

        viewModel.getProducts()

        assertTrue(viewModel.products.value is ProductState.Loading)

        advanceUntilIdle()

        val result = viewModel.products.value
        assertTrue(result is ProductState.Loaded)
        assertEquals(domainProducts.map { it.toVO() }, (result as ProductState.Loaded).data)
    }

    @Test
    fun `getProducts returns Error onFailure`() = runTest(testDispatcher) {
        coEvery { interactor.getProducts() } throws RuntimeException("Failure")

        viewModel.getProducts()
        advanceUntilIdle()

        val state = viewModel.products.value
        assertTrue(state is ProductState.Error)
        assertEquals("Failure", (state as ProductState.Error).error)
    }

    @Test
    fun `getProducts returns EmptyList State`() = runTest(testDispatcher) {
        coEvery { interactor.getProducts() } returns emptyList()

        viewModel.getProducts()
        advanceUntilIdle()

        val state = viewModel.products.value
        assertTrue(state is ProductState.Loaded)
        assertTrue((state as ProductState.Loaded).data.isEmpty())
    }

    @Test
    fun `getProducts calls Interactor Once`() = runTest(testDispatcher) {
        coEvery { interactor.getProducts() } returns emptyList()

        viewModel.getProducts()
        advanceUntilIdle()

        coVerify(exactly = 1) { interactor.getProducts() }
    }

    @Test
    fun `changeViewCount should call interactor with incremented count`() =
        runTest(testDispatcher) {
            val vo = ProductInListVO(
                guid = "1",
                image = "Image",
                name = "Name",
                price = "5 ₽",
                rating = 5.0,
                isFavorite = true,
                viewCount = 30,
                inCartCount = 100,
                isInCart = true
            )

            coEvery { interactor.updateProductViewCount(any(), any()) } returns Unit

            viewModel.changeViewCount(vo)
            advanceUntilIdle()

            coVerify { interactor.updateProductViewCount("1", 31) }
        }

    @Test
    fun `changeInCartCount should call interactor with passed values`() = runTest(testDispatcher) {
        coEvery { interactor.updateProductInCartCount(any(), any()) } returns Unit

        viewModel.changeInCartCount("1", 10)
        advanceUntilIdle()

        coVerify { interactor.updateProductInCartCount("1", 10) }
    }

    @Test
    fun `getProducts should emit Error if interactor fails`() = runTest(testDispatcher) {
        coEvery { interactor.getProducts() } throws RuntimeException("Test error")

        viewModel.getProducts()

        advanceUntilIdle()

        val state = viewModel.products.value
        assertTrue(state is ProductState.Error)
        assertEquals("Test error", (state as ProductState.Error).error)
    }
}