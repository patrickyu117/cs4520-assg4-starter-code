package com.cs4520.assignment4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    private lateinit var repository: ProductRepository

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _noProductsAvailable = MutableLiveData<Boolean>()
    val noProductsAvailable: LiveData<Boolean> get() = _noProductsAvailable

    fun init(repository: ProductRepository) {
        this.repository = repository
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val productList = repository.getProducts()
                if (productList.isNotEmpty()) {
                    _products.value = productList
                } else {
                    _noProductsAvailable.value = true
                }
            } catch (e: Exception) {
                _error.value = "Error fetching data: ${e.message}"
            } finally {
                _loading.value = false

            }
        }
    }
}