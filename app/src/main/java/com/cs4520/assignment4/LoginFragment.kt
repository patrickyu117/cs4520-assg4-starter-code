package com.cs4520.assignment4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment4.databinding.FragmentLoginBinding

// The NavController switches between the fragments based off of the nav graph
class LoginFragment : Fragment() {

    // Creates the binding object
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflates the view
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setting the on click listener for the login button
        binding.loginButton.setOnClickListener {
            // Grabs the username and password from the text boxes
            val username = binding.usernameText.text.toString()
            val password = binding.passwordText.text.toString()
            // Checks if the username and password are correct
            if (username == "admin" && password == "admin") {
                // Display success login message
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
                // Clear the username and password after logging in
                binding.usernameText.text.clear()
                binding.passwordText.text.clear()
            } else {
                // Display failed login message
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}