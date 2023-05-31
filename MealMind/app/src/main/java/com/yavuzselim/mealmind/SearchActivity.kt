package com.yavuzselim.mealmind

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yavuzselim.mealmind.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding
    private var firestore = Firebase.firestore
    private var mealLiveList = MutableLiveData<ArrayList<MealModel>>()
    private val isLoading = MutableLiveData<Boolean>()
    private var mealLastList = ArrayList<MealModel>()
    private lateinit var mealAdapter: MealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealAdapter = MealAdapter(arrayListOf(),object : MealClickListener{
            override fun clickedMeal(mealModel: MealModel) {
                super.clickedMeal(mealModel)
                val intent = Intent(this@SearchActivity,MealDetailActivity::class.java)
                intent.putExtra("mealModel", mealModel)
                startActivity(intent)
            }
        })
        binding.rvSearch.adapter = mealAdapter
        binding.rvSearch.layoutManager = LinearLayoutManager(this)
        binding.rvSearch.setHasFixedSize(true)
        observeLiveData()
        handleClick()
    }

    private fun getSearchMeals(mealName: String) {
        val docIdRef = firestore.collection("Breakfast")
            .whereEqualTo("mealName",mealName)
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
                mealLastList.addAll(mealList)
              //  mealLiveList.value = mealList
            }
            .addOnFailureListener { exception ->
                isLoading.postValue(false)
                println("Error getting documents: ${exception.localizedMessage}")
            }
        val docIdRef2 = firestore.collection("Dinner")
            .whereEqualTo("mealName",mealName)
        docIdRef2.get()
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
                mealLastList.addAll(mealList)
           //     mealLiveList.value = mealList
            }
            .addOnFailureListener { exception ->
                isLoading.postValue(false)
                println("Error getting documents: ${exception.localizedMessage}")
            }
        val docIdRef3 = firestore.collection("Lunch")
            .whereEqualTo("mealName",mealName)
        docIdRef3.get()
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
                mealLastList.addAll(mealList)
            //    mealLiveList.value = mealList
            }
            .addOnFailureListener { exception ->
                isLoading.postValue(false)
                println("Error getting documents: ${exception.localizedMessage}")
            }
        val docIdRef4 = firestore.collection("Dessert")
            .whereEqualTo("mealName",mealName)
        docIdRef4.get()
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
                mealLastList.addAll(mealList)
             //   mealLiveList.value = mealList
            }
            .addOnFailureListener { exception ->
                isLoading.postValue(false)
                println("Error getting documents: ${exception.localizedMessage}")
            }

        mealLiveList.value = mealLastList
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
        binding.ivSearch.setOnClickListener {
            if (binding.etSearch.text.toString().length <3){
                Toast.makeText(this,"Meal name lenght must be long 2 characters",Toast.LENGTH_SHORT).show()
            }else{
                getSearchMeals(binding.etSearch.text.toString())
              //  binding.etSearch.text.clear()
                 mealLastList.clear()
            }
        }
    }
}