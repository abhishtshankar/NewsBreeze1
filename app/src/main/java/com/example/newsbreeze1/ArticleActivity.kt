package com.example.newsbreeze1

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class ArticleActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ARTICLE_URL = "extra_article_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        val articleUrl = intent.getStringExtra(EXTRA_ARTICLE_URL)
        if (articleUrl != null) {
            webView.loadUrl(articleUrl)
        }
    }
}


