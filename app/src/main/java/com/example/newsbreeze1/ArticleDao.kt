package com.example.newsbreeze1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertArticle(article: ArticleEntity)
}

class ArticleRepository(private val articleDao: ArticleDao) {
    val allArticles: LiveData<List<ArticleEntity>> = articleDao.getAllArticles()

     fun insertArticle(article: ArticleEntity) {
        articleDao.insertArticle(article)
    }
}