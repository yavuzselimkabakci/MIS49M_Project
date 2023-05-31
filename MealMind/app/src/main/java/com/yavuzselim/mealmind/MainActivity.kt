package com.yavuzselim.mealmind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ShareCompat.IntentBuilder
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yavuzselim.mealmind.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var firestore = Firebase.firestore
    private var mealLiveList = MutableLiveData<ArrayList<MealModel>>()
    private val isLoading = MutableLiveData<Boolean>()
    private lateinit var mealModel : MealModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleClick()

        val categoryList = arrayListOf("Breakfast","Dinner","Lunch","Dessert")
        val random = (0..3).random()
        getRandomMeals(categoryList[random])
        observeLiveData()
    }

    private fun handleClick() {
        binding.ivFavourite.setOnClickListener {
            startActivity(Intent(this, FavouriteActivity::class.java))
            finish()
        }

        binding.flOne.setOnClickListener {
            intentToMealActivity("Breakfast")
        }

        binding.flTwo.setOnClickListener {
            intentToMealActivity("Dinner")
        }

        binding.flFour.setOnClickListener {
            intentToMealActivity("Lunch")
        }

        binding.flFive.setOnClickListener {
            intentToMealActivity("Dessert")
        }

        binding.ivSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            finish()
        }

        binding.cvRecommend.setOnClickListener {
            val intent = Intent(this@MainActivity,MealDetailActivity::class.java)
            intent.putExtra("mealModel",mealModel )
            startActivity(intent)
        }

    }

    private fun intentToMealActivity(mealCategory: String) {
        val intent = Intent(this, MealActivity::class.java)
        intent.putExtra("mealCategory", mealCategory)
        startActivity(intent)
        finish()
    }

    private fun getRandomMeals(category: String) {
        val docIdRef = firestore.collection(category)
        docIdRef.get()
            .addOnSuccessListener { documents ->
                val mealList = ArrayList<MealModel>()
                for (document in documents) {
                    val mealName = document.data["mealName"] as? String
                    val mealTime = document.data["mealTime"] as? String
                    val mealRecipe = document.data["mealRecipe"] as? ArrayList<String>
                    val mealIngredients = document.data["mealIngredients"] as? ArrayList<String>
                    val mealDesc = document.data["mealDesc"] as? String
                    val imageUrl = document.data["imageUrl"] as? String
                    val isFavourite = document.data["isFavourite"] as? Boolean
                    val mealId = document.data["mealId"] as? String
                    mealList.add(MealModel(mealName = mealName, mealTime = mealTime, mealImageUrl = imageUrl, mealIngredients = mealIngredients, mealRecipe = mealRecipe, mealDesc = mealDesc, isFavourite = isFavourite, mealId = mealId))
                }
                mealLiveList.value = mealList
            }
            .addOnFailureListener { exception ->
                isLoading.postValue(false)
                println("Error getting documents: ${exception.localizedMessage}")
            }
    }


    private fun observeLiveData(){
        mealLiveList.observe(this){ mealList ->
            val size = mealList.size - 1
            val random = (0..size).random()
            val randomMeal = mealList[random]
            mealModel = randomMeal
            binding.tvRecommendMealName.text = randomMeal.mealName

            Glide
                .with(this@MainActivity)
                .load(randomMeal.mealImageUrl)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(binding.ivMealRecomend)

        }
    }


}