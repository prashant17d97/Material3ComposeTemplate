package com.prashant.material3_compose_template.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val name: String,
    val email: String
)