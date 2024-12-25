package com.omarkarimli.omarproject.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.omarkarimli.omarproject.adapter.ProductAdapter
import com.omarkarimli.omarproject.adapter.ProductSecondAdapter
import com.omarkarimli.omarproject.api.RetrofitClient
import com.omarkarimli.omarproject.databinding.FragmentHomeBinding
import com.omarkarimli.omarproject.model.ProductModel
import com.omarkarimli.omarproject.model.ProductModelSecond
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private val api = RetrofitClient()
    private val adapter = ProductAdapter()
    private val adapterSecond = ProductSecondAdapter()

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Horizontal
        api.createService().getProducts().enqueue(object : retrofit2.Callback<List<ProductModel>> {
            override fun onFailure(
                call: Call<List<ProductModel>>,
                response: Throwable
            ) {
                Toast.makeText(
                    context,
                    response.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<List<ProductModel>>,
                response: Response<List<ProductModel>>
            ) {
                val products = response.body()

                if (response.isSuccessful) {
                    products?.let {
                        binding.progressBarHorizontal.visibility = View.GONE
                        binding.rvHorizontal.adapter = adapter
                        adapter.updateList(products)

                        adapter.onItemClick = { productId ->
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToProductFragment(productId)
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        })

        //  Vertical
        api.createServiceSecond().getProductsSecond().enqueue(object : retrofit2.Callback<List<ProductModelSecond>> {
            override fun onFailure(call: Call<List<ProductModelSecond>>, response: Throwable) {
                Toast.makeText(
                    context,
                    response.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<List<ProductModelSecond>>,
                response: Response<List<ProductModelSecond>>
            ) {
                val productsSecond = response.body()
                if (response.isSuccessful) {
                    productsSecond?.let {
                        binding.progressBarVertical.visibility = View.GONE
                        binding.rvVertical.adapter = adapterSecond
                        adapterSecond.updateList(productsSecond.slice(0..9))
                    }
                }
            }
        })
    }
}