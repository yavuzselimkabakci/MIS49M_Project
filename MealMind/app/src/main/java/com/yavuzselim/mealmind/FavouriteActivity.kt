package com.yavuzselim.mealmind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yavuzselim.mealmind.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding

    private var firestore = Firebase.firestore
    private var mealLiveList = MutableLiveData<ArrayList<MealModel>>()
    private val isLoading = MutableLiveData<Boolean>()
    private lateinit var mealAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFavouriteMeals()

        mealAdapter = MealAdapter(arrayListOf(),object : MealClickListener{
            override fun clickedMeal(mealModel: MealModel) {
                super.clickedMeal(mealModel)
                val intent = Intent(this@FavouriteActivity,MealDetailActivity::class.java)
                intent.putExtra("mealModel", mealModel)
                startActivity(intent)
            }
        })
        binding.rvFavourite.adapter = mealAdapter
        binding.rvFavourite.layoutManager = LinearLayoutManager(this)
        binding.rvFavourite.setHasFixedSize(true)
        observeLiveData()
        handleClick()
    }

    private fun getFavouriteMeals() {
        val docIdRef = firestore.collection("Favourites")
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
                    mealList.add(MealModel(mealName = mealName, mealTime = mealTime, mealImageUrl = imageUrl, mealIngredients = mealIngredients, mealRecipe = mealRecipe, mealDesc =mealDesc, isFavourite = isFavourite, mealId = mealId))
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
            if(mealList.size != 0 && mealList != null){
                mealAdapter.updateMealList(mealList)
            }
        }
    }

    private fun handleClick(){
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    override fun onRestart() {
        super.onRestart()
        getFavouriteMeals()
        println("onrestart")
    }
}