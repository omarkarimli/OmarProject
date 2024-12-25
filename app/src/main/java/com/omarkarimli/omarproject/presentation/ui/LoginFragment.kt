package com.omarkarimli.omarproject.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omarkarimli.omarproject.api.RetrofitClient
import com.omarkarimli.omarproject.databinding.FragmentLoginBinding
import com.omarkarimli.omarproject.model.LoginRequestModel
import com.omarkarimli.omarproject.model.LoginResponseModel
import retrofit2.Call
import retrofit2.Response

class LoginFragment : Fragment() {

    private val api = RetrofitClient().createService()

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Function to check both EditTexts and enable/disable the button
        fun checkFields() {
            val isUsernameNotEmpty = binding.editTextUsername.text.toString().trim().isNotEmpty()
            val isPasswordNotEmpty = binding.editTextPassword.text.toString().trim().isNotEmpty()
            binding.buttonLogin.isEnabled = isUsernameNotEmpty && isPasswordNotEmpty
        }

        // Add TextWatcher for both EditTexts
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFields()
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed
            }
        }

        binding.editTextUsername.addTextChangedListener(textWatcher)
        binding.editTextPassword.addTextChangedListener(textWatcher)

        binding.buttonLogin.setOnClickListener {
            if (binding.editTextUsername.text.isNotEmpty() && binding.editTextPassword.text!!.isNotEmpty()) {
                loginRequest(
                    binding.editTextUsername.text.toString(),
                    binding.editTextPassword.text.toString()
                )
            }
        }
    }

    private fun loginRequest(username: String, password: String) {
        binding.loading.visibility = View.VISIBLE

        val loginRequestModel = LoginRequestModel(username, password)
        api.loginUser(loginRequestModel).enqueue(object : retrofit2.Callback<LoginResponseModel>{
            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                if (response.isSuccessful) {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(context, "Successful login", Toast.LENGTH_SHORT).show()

                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                } else {
                    Toast.makeText(context, "Failed login", Toast.LENGTH_SHORT).show()

                    binding.loading.visibility = View.GONE
                }
            }
        })
    }
}