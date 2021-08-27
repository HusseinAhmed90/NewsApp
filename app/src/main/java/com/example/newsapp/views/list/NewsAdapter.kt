package com.example.newsapp.views.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.core.data.NewsModel
import com.example.newsapp.databinding.NewsListItemBinding

class NewsAdapter(val clickListener: NewsItemListener) : ListAdapter<NewsModel, NewsAdapter.NewsModelViewHolder>(DiffCallback) {

    class NewsModelViewHolder(private var binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsItem: NewsModel, clickListener: NewsItemListener) {
            binding.item = newsItem
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.publishedAt == newItem.publishedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsModelViewHolder {
        return NewsModelViewHolder(NewsListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: NewsModelViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class NewsItemListener (val clickListener: (item: NewsModel) -> Unit) {
        fun onClick(item: NewsModel) = clickListener(item)
    }
}