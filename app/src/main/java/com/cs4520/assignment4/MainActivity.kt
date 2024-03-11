package com.cs4520.assignment4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cs4520.assignment4.databinding.ActivityMainBinding

// Creates the binding object
private lateinit var binding : ActivityMainBinding
// Main Activity class using view binding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflates the view
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}