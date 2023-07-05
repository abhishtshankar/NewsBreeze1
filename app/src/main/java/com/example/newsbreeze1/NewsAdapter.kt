package com.example.newsbreeze1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var articles: List<Article> = emptyList()
    private var onArticleClickListener: OnArticleClickListener? = null

    fun setOnArticleClickListener(listener: OnArticleClickListener) {
        onArticleClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun setArticles(articles: List<Article>) {
        this.articles = articles
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val articleImageView: ImageView = itemView.findViewById(R.id.articleImageView)
        val saveButton: Button = itemView.findViewById(R.id.saveButton)
        val readButton: Button = itemView.findViewById(R.id.readButton)

        fun bind(article: Article) {
            titleTextView.text = article.title
            dateTextView.text = article.publishedAt
            descriptionTextView.text = article.description

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(articleImageView)

            saveButton.setOnClickListener {
                // Handle save button click
                onArticleClickListener?.onSaveButtonClick(article)
            }

            readButton.setOnClickListener {
                // Handle read button click
                onArticleClickListener?.onReadButtonClick(article)
            }

            itemView.setOnClickListener {
                // Handle item click
                onArticleClickListener?.onArticleClick(article)
            }
        }
    }

    interface OnArticleClickListener {
        fun onArticleClick(article: Article)
        fun onSaveButtonClick(article: Article)
        fun onReadButtonClick(article: Article)
    }
}
