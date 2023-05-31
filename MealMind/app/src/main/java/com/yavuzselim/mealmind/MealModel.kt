package com.yavuzselim.mealmind

import java.io.Serializable

data class MealModel(
    var mealName: String? = null,
    var mealTime: String? = null,
    var mealIngredients: ArrayList<String>? = null,
    var mealRecipe: ArrayList<String>? = null,
    var mealImageUrl: String? = null,
    var mealDesc: String? = null,
    var isFavourite: Boolean? = null,
    var mealId: String? = null
): Serializable