package com.example.feature_products.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.core_model.domain.ProductState
import com.example.core_model.presentation.ProductInListVO
import com.example.core_navigation.NavigationProvider
import com.example.core_utils.adapter.ProductsAdapter
import com.example.core_utils.viewModel.ViewModelFactory
import com.example.feature_products.databinding.FragmentProductsBinding
import com.example.feature_products.di.FeatureProductsComponentHolder
import com.example.feature_products.presentation.viewModel.ProductsViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding == null")

    @Inject
    lateinit var navigationProvider: NavigationProvider

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ProductsViewModel

    private val adapter by lazy {
        ProductsAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FeatureProductsComponentHolder.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[ProductsViewModel::class.java]

        with(binding) {
            rvProductList.adapter = adapter
            swipeRefresh.setOnRefreshListener {
                viewModel.getProducts()
            }
        }
        setupOnProductClickListener(adapter)
        setupUpdateProductInCartCount(adapter)
        setupOnShoppingCartClickListener()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getProducts()

                viewModel.products.collect { productState ->
                    when (productState) {
                        is ProductState.Idle -> {}

                        is ProductState.Loading -> {
                            binding.progressBarProductList.isVisible = true
                        }

                        is ProductState.Loaded -> {
                            with(binding) {
                                progressBarProductList.isVisible = false
                                swipeRefresh.isRefreshing = false
                            }
                            adapter.submitList(productState.data)
                        }

                        is ProductState.Error -> {
                            with(binding) {
                                progressBarProductList.isVisible = false
                                swipeRefresh.isRefreshing = false
                            }
                            val toastText = productState.error
                            showToast(toastText)
                        }
                    }
                }

            }
        }
    }

    private fun showToast(toastText: String) {
        Toast.makeText(
            requireContext(),
            toastText,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupOnProductClickListener(adapter: ProductsAdapter) {
        adapter.setupOnProductClickListener(
            object : ProductsAdapter.OnProductClickListener {
                override fun onProductClick(product: ProductInListVO) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.changeViewCount(product)
                        navigationProvider.openProductDetails(product.guid)
                    }
                }
            }
        )
    }

    private fun setupUpdateProductInCartCount(adapter: ProductsAdapter) {
        adapter.setupUpdateProductInCartCount(
            object : ProductsAdapter.UpdateProductInCartCount {
                override fun updateProductInCart(guid: String, inCartCount: Int) {
                    viewModel.changeInCartCount(guid, inCartCount)
                }
            }
        )
    }

    private fun setupOnShoppingCartClickListener() {
        binding.shoppingCartFAB.setOnClickListener {
            navigationProvider.openShoppingCart()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProductsFragment()
    }
}