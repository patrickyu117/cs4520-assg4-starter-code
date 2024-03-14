package com.cs4520.assignment4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ViewModel class for the product list
class ProductListViewModel : ViewModel() {

    private lateinit var repo: ProductRepository

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _noProducts = MutableLiveData<Boolean>()
    val noProducts: LiveData<Boolean> get() = _noProducts

    // Initialize the repository
    fun initialize(repository: ProductRepository) {
        this.repo = repository
    }

    // Retrieves the product list from the repository in a coroutine
    fun fetchProducts() {
        viewModelScope.launch {
            // Start loading and set the loading value to true
            _loading.value = true
            try {
                // Try fetching the products from the repo
                val productList = repo.getProducts()
                // Make sure the product list is not null or empty
                if (!productList.isNullOrEmpty()) {
                    _products.value = productList
                } else {
                    // If the product list is empty, set the no products value to true
                    _noProducts.value = true
                }
            } catch (e: Exception) {
                // Catch any error exception and set it to the error value
                _error.value = e.message
            } finally {
                // After loading successfully or unsuccessfully, set the loading value to false
                _loading.value = false

            }
        }
    }
}