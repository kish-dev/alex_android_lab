package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.data.di.ServiceLocator
import alex.android.lab.databinding.FragmentPdpBinding
import alex.android.lab.domain.entities.ProductState
import alex.android.lab.presentation.viewModel.PDPViewModel
import alex.android.lab.presentation.viewModel.viewModelCreator
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PDPFragment : Fragment() {

    private var _binding: FragmentPdpBinding? = null
    private val binding: FragmentPdpBinding
        get() = checkNotNull(_binding)

    private val viewModel: PDPViewModel by viewModelCreator {
        PDPViewModel(ServiceLocator.provideProductsInteractor(requireContext().applicationContext))
    }

    private var guid: String? = null

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
        viewLifecycleOwner.lifecycleScope.launch {

            guid.let { viewModel.getDetailProduct(it) }

            viewModel.detailProduct.collect { productState ->
                when (productState) {

                    is ProductState.Idle -> {}

                    is ProductState.Loading -> {
                        binding.progressBarProduct.isVisible = true
                    }

                    is ProductState.Loaded -> {
                        binding.progressBarProduct.isVisible = false
                        val product = productState.data
                        setupDetailProduct(product)
                        setupOnFavouriteClickListener(product)
                        setupUpdateProductInCartCount(product)
                    }

                    is ProductState.Error -> {
                        binding.progressBarProduct.isVisible = false
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