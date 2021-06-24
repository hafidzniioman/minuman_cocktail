package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.utils.Commons

@Entity(tableName = Commons.DRINKS)
data class Fav(@PrimaryKey(autoGenerate = true) var favId: Int?,
               var drinkId: String? = "",
               var drinkName: String? = "",
               var drinkPhoto: String? = "")