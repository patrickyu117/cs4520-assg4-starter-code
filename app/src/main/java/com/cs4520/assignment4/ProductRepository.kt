package com.cs4520.assignment4

// Local product repository database
class ProductRepository(private val apiService: ApiService, private val productDao: ProductDao) {

    // Get the products from the API
    suspend fun getProducts(): List<Product> {
        try {
            // Call the API to get the products directly
            val products = apiService.getProducts()

            // If the JSON fetched is not a product i.e. it is null or has null fields
            // we know this is the instance where the API call returns {message: "Random error occurred"}
            if (products.firstOrNull()?.name == null) {
                // Throw an exception to pass on the error mesage
                throw NoProductsException("Random error occurred")
            }

            // Filter out duplicate products
            val distinctProducts = products.distinctBy { it.name }

            // Filter out invalid products
            val validProducts = distinctProducts.filter { it.isValidProduct() }

            // Insert products into the local room database
            productDao.insertAll(validProducts)

            // Return the list of products
            return products
        } catch (e: Exception) {
            // Check the error message to see if it because the API call returned something that is
            // not a product i.e. the error message. Throws an exception if we know the error is not from
            // being offline
            if (e.message == "Random error occurred" || e.message == "HTTP 403" || e.message == "HTTP 404") {
                throw NoProductsException("Random error occurred")
            } else {
                // Return the local database once we know the error was because the phone is offline
                return productDao.getAllProducts()
            }
        }
    }
}

class NoProductsException(message: String) : Exception(message)