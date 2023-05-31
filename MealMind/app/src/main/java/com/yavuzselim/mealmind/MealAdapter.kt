package com.yavuzselim.mealmind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yavuzselim.mealmind.databinding.ItemMealBinding

class MealAdapter(private val mealList: ArrayList<MealModel>,private val mealClickListener: MealClickListener): RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private val lastMealList = mealList
    class MealViewHolder(val binding: ItemMealBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MealViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.binding.tvMealName.text = lastMealList[position].mealName

        Glide
            .with(holder.itemView.context)
            .load(lastMealList[position].mealImageUrl)
            .centerCrop()
            .placeholder(R.drawable.loading)
            .into(holder.binding.ivMealRecomend)

        holder.itemView.setOnClickListener {
            mealClickListener.clickedMeal(mealModel = lastMealList[position])
        }

    }

    override fun getItemCount() = mealList.size

    fun updateMealList(newMealList: ArrayList<MealModel>){
        lastMealList.clear()
        lastMealList.addAll(newMealList)
        notifyDataSetChanged()
    }
}