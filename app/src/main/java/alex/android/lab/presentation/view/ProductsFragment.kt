package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.data.di.ServiceLocator
import alex.android.lab.databinding.FragmentProductsBinding
import alex.android.lab.domain.entities.ProductsListState
import alex.android.lab.presentation.view.adapters.ProductsAdapter
import alex.android.lab.presentation.viewModel.ProductsViewModel
import alex.android.lab.presentation.viewModel.viewModelCreator
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding == null")

    private val viewModel: ProductsViewModel by viewModelCreator {
        ProductsViewModel(ServiceLocator.provideProductsInteractor(requireContext().applicationContext))
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

        val adapter = ProductsAdapter()
        binding.rvProductList.adapter = adapter
        setupOnProductClickListener(adapter)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getProducts()

                viewModel.products.collect { productState ->
                    when (productState) {
                        ProductsListState.Idle -> {}

                        ProductsListState.Loading -> {
                            binding.progressBarProductList.isVisible = true
                        }

                        is ProductsListState.Loaded -> {
                            binding.progressBarProductList.isVisible = false
                            adapter.submitList(productState.products)
                        }

                        is ProductsListState.Error -> {
                            binding.progressBarProductList.isVisible = false
                            val toastText = productState.error
                            showToast(toastText)
                            delay(TIMEOUT_RETRY_MILLIS).also {
                                viewModel.getProducts()
                            }
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
                        val detailProductFragment = PDPFragment.newInstance(product.guid)
                        launchFragment(detailProductFragment)
                    }
                }
            }
        )
    }

    private fun launchFragment(fragment: Fragment) {
        parentFragmentManager.popBackStack()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val TIMEOUT_RETRY_MILLIS = 5000L
    }
}