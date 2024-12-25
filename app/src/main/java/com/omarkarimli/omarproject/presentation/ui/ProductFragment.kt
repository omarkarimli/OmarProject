package com.omarkarimli.omarproject.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.omarkarimli.omarproject.api.RetrofitClient
import com.omarkarimli.omarproject.databinding.FragmentProductBinding
import com.omarkarimli.omarproject.model.ProductModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response

class ProductFragment : Fragment() {
    val args: ProductFragmentArgs by navArgs()

    val api = RetrofitClient().createService()

    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        api.getProductById(args.productId).enqueue(object : retrofit2.Callback<ProductModel>{
            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                val product = response.body()
                if (response.isSuccessful) {
                    product?.let {
                        binding.textViewTitle.text = product.title
                        binding.textViewDesc.text = product.description
                        binding.textViewCategory.text = product.category
                        binding.textViewRating.text = product.rating?.rate.toString()
                        binding.textViewCount.text = product.rating?.count.toString()
                        binding.textViewPrice.text = product.price.toString()

                        Picasso
                            .get()
                            .load(product.image)
                            .into(binding.imageView)

                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }
}