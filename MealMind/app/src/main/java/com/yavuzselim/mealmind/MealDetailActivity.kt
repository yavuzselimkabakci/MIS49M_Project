package com.yavuzselim.mealmind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yavuzselim.mealmind.databinding.ActivityMealDetailBinding

class MealDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailBinding
    private lateinit var mealModel : MealModel
    private lateinit var recipeAdapter: CommonAdapter
    private lateinit var ingredientsAdapter: RecipeAdapter
    private var firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealModel = intent.getSerializableExtra("mealModel") as MealModel
        binding.tvDesc.text = mealModel.mealDesc
        recipeAdapter = CommonAdapter(mealModel.mealRecipe!!)
        ingredientsAdapter = RecipeAdapter(mealModel.mealIngredients!!)

        binding.rvRecipe.adapter = recipeAdapter
        binding.rvRecipe.layoutManager = LinearLayoutManager(this)
        binding.rvRecipe.setHasFixedSize(true)

        binding.rvIngredients.adapter = ingredientsAdapter
        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        binding.rvIngredients.setHasFixedSize(true)
        binding.tvTitle.text = mealModel.mealName
        binding.tvTime.text = "Time: ${mealModel.mealTime}"

        Glide
            .with(this)
            .load(mealModel.mealImageUrl)
            .centerCrop()
            .placeholder(R.drawable.loading)
            .into(binding.ivMeal)


        when(mealModel.isFavourite){
            true ->  binding.ivAddFav.setImageResource(R.drawable.ic_baseline_favorite_24_white)
            false ->  binding.ivAddFav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            else -> {}
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivAddFav.setOnClickListener {
            if (mealModel.isFavourite == true){
                unAddFavourite()
            }else if(mealModel.isFavourite == false){
                addFavourite()
            }
        }


    }
    private fun addFavourite(){
        val meal = hashMapOf(
            "mealName" to mealModel.mealName,
            "mealTime" to mealModel.mealTime,
            "mealRecipe" to mealModel.mealRecipe,
            "mealIngredients" to mealModel.mealIngredients,
            "imageUrl" to mealModel.mealImageUrl,
            "isFavourite" to true,
            "mealDesc" to mealModel.mealDesc,
            "mealId" to mealModel.mealId
        )

        firestore.collection("Favourites").document(mealModel.mealId!!)
            .set(meal)
            .addOnSuccessListener {
                Toast.makeText(this,"The meal added your favourite list.",Toast.LENGTH_SHORT).show()
                binding.ivAddFav.setImageResource(R.drawable.ic_baseline_favorite_24_white)
            }
            .addOnFailureListener {
                Toast.makeText(this,"The meal not added your favourite list.",Toast.LENGTH_SHORT).show()
              }
    }
    private fun unAddFavourite(){
        firestore.collection("Favourites").document(mealModel.mealId!!)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this,"The meal deleted your favourite list.",Toast.LENGTH_SHORT).show()
                binding.ivAddFav.setImageResource(R.drawable.ic_baseline_favorite_border_24) }
            .addOnFailureListener {
                Toast.makeText(this,"The meal not deleted your favourite list.",Toast.LENGTH_SHORT).show()
                }
    }



}