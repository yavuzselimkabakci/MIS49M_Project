package com.yavuzselim.mealmind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yavuzselim.mealmind.databinding.ActivityMealBinding

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private var mealCategory = ""
    private var firestore = Firebase.firestore
    private var mealLiveList = MutableLiveData<ArrayList<MealModel>>()
    private val isLoading = MutableLiveData<Boolean>()
    private lateinit var mealAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealCategory = intent.getStringExtra("mealCategory").toString()
        getMeals(category = mealCategory)
        binding.tvCategory.text = mealCategory

        mealAdapter = MealAdapter(arrayListOf(),object : MealClickListener{
            override fun clickedMeal(mealModel: MealModel) {
                super.clickedMeal(mealModel)
                val intent = Intent(this@MealActivity,MealDetailActivity::class.java)
                intent.putExtra("mealModel", mealModel)
                startActivity(intent)
            }
        })
        binding.rvMeal.adapter = mealAdapter
        binding.rvMeal.layoutManager = LinearLayoutManager(this)
        binding.rvMeal.setHasFixedSize(true)
        observeLiveData()
        handleClick()
    }

    private fun getMeals(category: String) {
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
}