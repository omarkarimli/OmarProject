package com.omarkarimli.omarproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omarkarimli.omarproject.databinding.ItemProductBinding
import com.omarkarimli.omarproject.model.ProductModel
import com.squareup.picasso.Picasso

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var onItemClick: ((Int) -> Unit)? = null

    val list = arrayListOf<ProductModel>()

    inner class ProductViewHolder(val binding: ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layout = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = list[position]

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product.id!!)
        }

        // Data binding
//        holder.binding.product = product

        holder.binding.textViewTitle.text = product.title
        holder.binding.textViewDesc.text = product.description


        // Picasso
        Picasso
            .get()
            .load(product.image)
            .into(holder.binding.imageView)
    }

    fun updateList(newList: List<ProductModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}