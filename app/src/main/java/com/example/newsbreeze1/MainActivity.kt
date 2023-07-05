package com.example.newsbreeze1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), NewsAdapter.OnArticleClickListener {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var articles: List<Article>
    private lateinit var searchEditText: EditText

    private lateinit var articleRepository: ArticleRepository
    private lateinit var allArticles: LiveData<List<ArticleEntity>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchEditText = findViewById(R.id.searchEditText)

        val newsRecyclerView: RecyclerView = findViewById(R.id.newsRecyclerView)
        newsAdapter = NewsAdapter()
        newsRecyclerView.adapter = newsAdapter
        newsRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.articles.observe(this, { articles ->
            this.articles = articles
            newsAdapter.setArticles(articles)
        })

        val articleDao = ArticleDatabase.getDatabase(application).articleDao()
        articleRepository = ArticleRepository(articleDao)
        allArticles = articleRepository.allArticles

        newsAdapter.setOnArticleClickListener(this)

        viewModel.fetchBreakingNews()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                searchNews(query)
            }
        })
    }

    private fun searchNews(query: String) {
        val filteredArticles = articles.filter { article ->
            article.title.contains(query, true)
        }
        newsAdapter.setArticles(filteredArticles)
    }

    override fun onArticleClick(article: Article) {
        showFullArticle(article.articleUrl)
    }

    override fun onSaveButtonClick(article: Article) {
        val articleEntity = ArticleEntity(
            title = article.title,
            publishedAt = article.publishedAt,
            description = article.description,
            urlToImage = article.urlToImage,
            articleUrl = article.articleUrl
        )
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                articleRepository.insertArticle(articleEntity)
            }
            Toast.makeText(this@MainActivity, "Article saved.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onReadButtonClick(article: Article) {
        // Handle read button click
        val articleUrl = article.articleUrl
        showFullArticle(articleUrl)
    }

    private fun showFullArticle(articleUrl: String) {
        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(ArticleActivity.EXTRA_ARTICLE_URL, articleUrl)
        startActivity(intent)
    }
}

