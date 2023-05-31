package com.yavuzselim.mealmind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yavuzselim.mealmind.databinding.ItemRecipeBinding

class RecipeAdapter(private val commonList: ArrayList<String>): RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    val lastList = commonList

    class RecipeViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.binding.tvMeal.text = lastList[position]
    }

    override fun getItemCount() = lastList.size
}