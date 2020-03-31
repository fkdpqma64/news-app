package com.android.newsapp.newslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.newsapp.R
import com.android.newsapp.databinding.NewsListItemBinding
import common.data.local.Item
import common.di.module.GlideApp
import common.lib.parse.keyWordsList

class NewsListAdapter :
    RecyclerView.Adapter<NewsListAdapter.ItemViewHolder>() {
    class ItemViewHolder(val mbind: NewsListItemBinding) : RecyclerView.ViewHolder(mbind.root)

    lateinit var items: List<Item>
    lateinit var clickListener: (item: Item) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        val viewHolder = ItemViewHolder( NewsListItemBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        items[position].let {
            with(holder.mbind) {
                txtNewsTitle.text = it.title
                val desc = if( it.newsData?.article != null && it.newsData!!.article?.trim() != "") it.newsData!!.article else it.newsData?.description
                txtNewsText.text = desc
                when {
                    desc != null && desc.trim() != "" -> {
                        val keyword = keyWordsList(desc)
                        it.keyWords.addAll(keyword)
                        txtKeyword1.text = keyword[0]
                        txtKeyword2.text = keyword[1]
                        txtKeyword3.text = keyword[2]
                    }
                    else -> {
                        txtKeyword1.text = ""
                        txtKeyword2.text = ""
                        txtKeyword3.text = ""
                    }
                }
                GlideApp.with(holder.itemView).load(it.newsData?.thumb).into(imgNews)
            }
        }
    }

}
