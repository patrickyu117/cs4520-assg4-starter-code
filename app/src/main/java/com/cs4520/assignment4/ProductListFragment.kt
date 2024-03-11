package com.cs4520.assignment4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment4.databinding.FragmentProductListBinding


class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductListViewModel
    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiService = ApiClient.apiService
        val productDao = ProductDatabase.getInstance(requireContext()).productDao()
        val repository = ProductRepository(apiService, productDao)

        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]
        viewModel.init(repository)

        productAdapter = ProductListAdapter(emptyList())
        binding.recyclerView.adapter = productAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.fetchProducts()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            // Update the entire dataset when receiving updates
            productAdapter = ProductListAdapter(products)
            binding.recyclerView.adapter = productAdapter
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.noProductsAvailable.observe(viewLifecycleOwner) { noProductsAvailable ->
            binding.noProducts.visibility = if (noProductsAvailable) View.VISIBLE else View.GONE
        }
    }
}
