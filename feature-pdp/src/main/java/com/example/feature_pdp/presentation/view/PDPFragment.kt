package com.example.feature_pdp.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.core_model.domain.ProductState
import com.example.core_model.presentation.ProductInListVO
import com.example.core_utils.R
import com.example.core_utils.viewModel.ViewModelFactory
import com.example.feature_pdp.databinding.FragmentPdpBinding
import com.example.feature_pdp.di.FeaturePDPComponentHolder
import com.example.feature_pdp.presentation.viewModel.PDPViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PDPFragment : Fragment() {

    private var _binding: FragmentPdpBinding? = null
    private val binding: FragmentPdpBinding
        get() = _binding ?: throw RuntimeException("FragmentPDPBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: PDPViewModel

    private var guid: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FeaturePDPComponentHolder.getComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPdpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[PDPViewModel::class.java]

        viewLifecycleOwner.lifecycleScope.launch {

            guid.let { viewModel.getDetailProduct(it) }

            viewModel.detailProduct.collect { productState ->
                when (productState) {

                    is ProductState.Idle -> {}

                    is ProductState.Loading -> {
                        binding.progressBarProduct.isVisible = true
                    }

                    is ProductState.Loaded -> {
                        with(binding) {
                            progressBarProduct.isVisible = false
                            swipeRefresh.isRefreshing = false
                        }
                        val product = productState.data
                        setupDetailProduct(product)
                        setupOnFavouriteClickListener(product)
                        setupUpdateProductInCartCount(product)
                    }

                    is ProductState.Error -> {
                        with(binding) {
                            progressBarProduct.isVisible = false
                            swipeRefresh.isRefreshing = false
                        }
                        val toastText = productState.error
                        showToast(toastText)
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

    private fun setupOnFavouriteClickListener(product: ProductInListVO) {
        binding.icFavouriteIV.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.changeFavouriteStatus(product)
            }
        }
    }

    private fun setupDetailProduct(product: ProductInListVO) {
        with(binding) {
            Glide.with(this@PDPFragment)
                .load(product.image)
                .into(productIV)
            nameTV.text = product.name
            priceTV.text = product.price
            ratingView.rating = product.rating.toFloat()
            ratingView.isVisible = true
            viewCountTV.text = String.format(product.viewCount.toString())
            icEyeIV.isVisible = true

            val favouriteResId = if (product.isFavorite) {
                R.drawable.ic_favorite_filled
            } else {
                R.drawable.ic_favorite
            }
            val favouriteIcon = ContextCompat.getDrawable(requireContext(), favouriteResId)
            icFavouriteIV.setImageDrawable(favouriteIcon)
            cartButton.setState(product.inCartCount)

            swipeRefresh.setOnRefreshListener {
                viewModel.getDetailProduct(guid)
            }
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(ARG_PRODUCT_ID)) {
            throw RuntimeException("Param ARG_PRODUCT_ID is absent")
        }
        guid = args.getString(ARG_PRODUCT_ID, ARG_EMPTY_SYMBOL)
    }

    private fun setupUpdateProductInCartCount(product: ProductInListVO) {
        binding.cartButton.updateProductInCartCount = { inCartCount ->
            viewModel.changeInCartCount(product.guid, inCartCount)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PRODUCT_ID = "productId"
        private const val ARG_EMPTY_SYMBOL = ""

        @JvmStatic
        fun newInstance(guid: String) =
            PDPFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PRODUCT_ID, guid)
                }
            }
    }
}