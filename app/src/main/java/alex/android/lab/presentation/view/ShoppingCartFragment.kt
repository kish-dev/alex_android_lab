package alex.android.lab.presentation.view

import alex.android.lab.LabApplication
import alex.android.lab.R
import alex.android.lab.databinding.FragmentShoppingCartBinding
import alex.android.lab.di.components.DaggerShoppingCartFragmentComponent
import alex.android.lab.domain.entities.ProductState
import alex.android.lab.presentation.view.adapters.ProductsAdapter
import alex.android.lab.presentation.viewModel.ShoppingCartViewModel
import alex.android.lab.presentation.viewModel.ViewModelFactory
import alex.android.lab.presentation.viewObject.ProductInListVO
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
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingCartFragment : Fragment() {

    private var _binding: FragmentShoppingCartBinding? = null
    private val binding: FragmentShoppingCartBinding
        get() = _binding ?: throw RuntimeException("FragmentShoppingCartBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        DaggerShoppingCartFragmentComponent.factory().create(
            (requireActivity().application as LabApplication).component
        )
    }

    private lateinit var viewModel: ShoppingCartViewModel

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentShoppingCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[ShoppingCartViewModel::class.java]

        val adapter = ProductsAdapter()
        with(binding) {
            rvProductList.adapter = adapter
            swipeRefresh.setOnRefreshListener {
                viewModel.getProducts()
            }
        }
        setupOnProductClickListener(adapter)
        setupUpdateProductInCartCount(adapter)

        viewModel = ViewModelProvider(this, viewModelFactory)[ShoppingCartViewModel::class.java]

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
                        val detailProductFragment = PDPFragment.newInstance(product.guid)
                        launchFragment(detailProductFragment)
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

    private fun launchFragment(fragment: Fragment) {
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

        @JvmStatic
        fun newInstance() =
            ShoppingCartFragment()
    }
}
