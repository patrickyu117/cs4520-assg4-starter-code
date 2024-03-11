package com.cs4520.assignment4

class ProductRepository(private val apiService: ApiService, private val productDao: ProductDao) {
    suspend fun getProducts(): List<Product> {
        try {
            // Call the API to get the products directly
            val products = apiService.getProducts()

            // Filter out duplicate products
            val distinctProducts = products.distinctBy { it.name }

            // Filter out invalid products
            val validProducts = distinctProducts.filter { it.isValidProduct() }

            // Save to database
            productDao.insertAll(validProducts)

            return products
        } catch (e: Exception) {
            // If API call fails (offline), retrieve data from the database
            return productDao.getAllProducts()
        }
    }


}