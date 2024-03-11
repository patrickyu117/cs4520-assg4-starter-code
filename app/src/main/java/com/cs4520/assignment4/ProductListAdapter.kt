package com.cs4520.assignment4

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment4.R.*
import com.cs4520.assignment4.databinding.ProductItemBinding

// Product list adapter
class ProductListAdapter(private val productList: List<Product>) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    // Inflates the layout
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapter.ViewHolder {
        return ViewHolder(ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // Gets the size of the list
    override fun getItemCount(): Int {
        return productList.size
    }

    // Bind the product to the viewholder depending on what type of product it is
    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        val product = productList[position]
        if (product.type == "Food") {
            holder.bindFood(product)
        } else if (product.type == "Equipment") {
            holder.bindEquipment(product)
        }
    }

    // Viewholder class
    inner class ViewHolder(val itemBinding: ProductItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {

            // Binding function for food
            @SuppressLint("SetTextI18n")
            fun bindFood(food: Product) {
                // Set the name text to what is in the list
                itemBinding.productName.text = food.name
                // If the expiry date is not null, display the expiry date text
                // otherwise make it invisible
                if (food.expiryDate != "null") {
                    itemBinding.productExpiryDate.text = food.expiryDate
                    itemBinding.productExpiryDate.visibility = View.VISIBLE
                } else {
                    itemBinding.productExpiryDate.visibility = View.GONE
                }
                // Set the price text
                itemBinding.productPrice.text = "$" + food.price.toString()
                // Set the image to the food image
                itemBinding.productImage.setImageResource(drawable.food)
                // Set the background color to light yellow
                itemBinding.cardView.setBackgroundResource(color.light_yellow)
            }

            // Binding function for equipment
            @SuppressLint("SetTextI18n")
            fun bindEquipment(equipment: Product) {
                // Sets name text to the equipment name
                itemBinding.productName.text = equipment.name
                // Sets the text of the price
                itemBinding.productPrice.text = "$" + equipment.price.toString()
                // Sets the image to the equipment
                itemBinding.productImage.setImageResource(drawable.equipment)
                // Sets the backgrund color to light red
                itemBinding.cardView.setBackgroundResource(color.light_red)
                // If the expiry date is not null, display the expiry date text
                // otherwise make it invisible
                if (equipment.expiryDate != "null") {
                    itemBinding.productExpiryDate.text = equipment.expiryDate
                    itemBinding.productExpiryDate.visibility = View.VISIBLE
                } else {
                    itemBinding.productExpiryDate.visibility = View.GONE
                }
            }


        }
}