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

// Product list fragment class
class ProductListFragment : Fragment() {

    private lateinit var viewModel: ProductListViewModel
    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductListAdapter

    // Create the view using view binding
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
        val repo = ProductRepository(apiService, productDao)

        // Initialize the product repository and viewmodel
        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]
        viewModel.initialize(repo)

        // Initialize the product adapter with an empty list
        productAdapter = ProductListAdapter(emptyList())
        binding.recyclerView.adapter = productAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fetch the products
        viewModel.fetchProducts()

        // Observe the view model actions
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            // Update the entire dataset when receiving updates
            productAdapter = ProductListAdapter(products)
            binding.recyclerView.adapter = productAdapter
        }

        // Display the error message if there is one
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }

        // Display the loading icon if the products are still loading
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Display the 'no products available' text if the product list is empty
        viewModel.noProducts.observe(viewLifecycleOwner) { noProductsAvailable ->
            binding.noProducts.visibility = if (noProductsAvailable) View.VISIBLE else View.GONE
        }
    }
}
