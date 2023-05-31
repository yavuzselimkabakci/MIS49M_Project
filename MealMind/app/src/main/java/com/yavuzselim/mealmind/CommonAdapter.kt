package com.yavuzselim.mealmind

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yavuzselim.mealmind.databinding.ItemCommonBinding

class CommonAdapter(private val commonList: ArrayList<String>): RecyclerView.Adapter<CommonAdapter.CommonViewHolder>() {

    private val lastCommonList = commonList

    class CommonViewHolder(val binding: ItemCommonBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val binding = ItemCommonBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        val positionForText = position + 1
        holder.binding.tvCount.text = "$positionForText)"
        holder.binding.tvMeal.text = lastCommonList[position]
    }

    override fun getItemCount() = lastCommonList.size


}