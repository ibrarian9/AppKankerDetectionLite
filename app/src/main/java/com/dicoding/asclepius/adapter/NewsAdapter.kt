package com.dicoding.asclepius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ListNewsBinding
import com.dicoding.asclepius.remote.retrofit.ArticlesItem

class NewsAdapter : ListAdapter<ArticlesItem, NewsAdapter.ViewHolder>(DIFF_CALBACK) {

    class ViewHolder(private val binding: ListNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem){
            binding.tvTitle.text = article.title
            binding.tvDeskripsi.text = article.description
            Glide.with(itemView.context).load(article.urlToImage).into(binding.ivNews)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    companion object {
        val DIFF_CALBACK = object : DiffUtil.ItemCallback<ArticlesItem>(){
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}