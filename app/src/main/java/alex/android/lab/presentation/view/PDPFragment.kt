package alex.android.lab.presentation.view

import alex.android.lab.data.di.ServiceLocator
import alex.android.lab.databinding.FragmentPdpBinding
import alex.android.lab.presentation.viewModel.PDPViewModel
import alex.android.lab.presentation.viewModel.viewModelCreator
import alex.android.lab.presentation.viewObject.ProductInListVO
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class PDPFragment : Fragment() {

    private var _binding: FragmentPdpBinding? = null
    private val binding: FragmentPdpBinding
        get() = _binding ?: throw RuntimeException("FragmentPdpBinding == null")

    private val vm: PDPViewModel by viewModelCreator {
        PDPViewModel(ServiceLocator.provideProductsInteractor())
    }

    private lateinit var guid: String
    private lateinit var product: ProductInListVO

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
        vm.getDetailProduct(guid)
        vm.detailProductLD.observe(this) {
            product = it
            setupDetailProduct()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupDetailProduct() {
        with(binding) {
            Glide.with(this@PDPFragment)
                .load(product.image)
                .into(productIV)
            nameTV.text = product.name
            priceTV.text = product.price
            ratingView.rating = product.rating.toFloat()
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(ARG_PRODUCT_ID)) {
            throw RuntimeException("Param ARG_PRODUCT_ID is absent")
        }
        guid = args.getString(ARG_PRODUCT_ID, ARG_EMPTY_SYMBOL)
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