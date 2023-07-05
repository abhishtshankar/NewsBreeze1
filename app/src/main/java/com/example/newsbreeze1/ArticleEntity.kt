package com.example.newsbreeze1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String?,
    val publishedAt: String?,
    val description: String?,
    val urlToImage: String?,
    val articleUrl: String?
)
