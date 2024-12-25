package com.omarkarimli.omarproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omarkarimli.omarproject.databinding.ItemProductBinding
import com.omarkarimli.omarproject.databinding.ItemProductVerticalBinding
import com.omarkarimli.omarproject.model.ProductModelSecond
import com.squareup.picasso.Picasso

class ProductSecondAdapter : RecyclerView.Adapter<ProductSecondAdapter.ProductSecondViewHolder>() {

    val list = arrayListOf<ProductModelSecond>()

    inner class ProductSecondViewHolder(val itemProductVerticalBinding: ItemProductVerticalBinding) :
        RecyclerView.ViewHolder(itemProductVerticalBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSecondViewHolder {
        val layout = ItemProductVerticalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ProductSecondViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductSecondViewHolder, position: Int) {
        val product = list[position]

        holder.itemProductVerticalBinding.product = product

        product.images?.get(0)?.let {
            Picasso
                .get()
                .load(it)
                .into(holder.itemProductVerticalBinding.imageView)
        }
    }

    fun updateList(newList: List<ProductModelSecond>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}