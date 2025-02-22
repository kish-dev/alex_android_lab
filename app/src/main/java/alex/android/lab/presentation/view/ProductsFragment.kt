package alex.android.lab.presentation.view

import alex.android.lab.R
import alex.android.lab.data.di.ServiceLocator
import alex.android.lab.databinding.FragmentProductsBinding
import alex.android.lab.presentation.view.adapters.ProductsAdapter
import alex.android.lab.presentation.viewModel.ProductsViewModel
import alex.android.lab.presentation.viewModel.viewModelCreator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding == null")

    private val vm: ProductsViewModel by viewModelCreator {
        ProductsViewModel(ServiceLocator.provideProductsInteractor())
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
        vm.productLD.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        adapter.onProductClickListener = object : ProductsAdapter.OnProductClickListener {
            override fun onProductClick(guid: String) {
                val detailProductFragment = PDPFragment.newInstance(guid)
                launchFragment(detailProductFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchFragment(fragment: Fragment) {
        parentFragmentManager.popBackStack()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}