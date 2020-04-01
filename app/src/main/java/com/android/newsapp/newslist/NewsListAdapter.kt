package com.android.newsapp.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.newsapp.R
import com.android.newsapp.databinding.NewsListItemBinding
import common.data.local.Item
import common.di.module.GlideApp
import common.lib.parse.keyWordsList

/**
 * 뉴스 리스트 어뎁터
 */
class NewsListAdapter :
    RecyclerView.Adapter<NewsListAdapter.ItemViewHolder>() {
    class ItemViewHolder(val mbind: NewsListItemBinding) : RecyclerView.ViewHolder(mbind.root)

    lateinit var items: List<Item>
    lateinit var clickListener: (item: Item) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        val viewHolder = ItemViewHolder(NewsListItemBinding.bind(view))
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
                val desc =
                    if (it.newsData?.article != null && it.newsData!!.article?.trim() != "") it.newsData!!.article else it.newsData?.description
                txtNewsText.text = desc
                when {
                    desc != null && desc.trim() != "" -> {
                        val keyword = keyWordsList(desc)
                        it.keyWords.addAll(keyword)
                        (keyword.indices).forEach {
                            when (it) {
                                0 -> {
                                    txtKeyword1.visibility = View.VISIBLE
                                    txtKeyword2.visibility = View.GONE
                                    txtKeyword3.visibility = View.GONE
                                    txtKeyword1.text = keyword[it]
                                }
                                1 -> {
                                    txtKeyword2.visibility = View.VISIBLE
                                    txtKeyword2.text = keyword[it]
                                }
                                2 -> {
                                    txtKeyword3.visibility = View.VISIBLE
                                    txtKeyword3.text = keyword[it]
                                }
                            }
                        }
                    }
                    else -> {
                        txtKeyword1.visibility = View.GONE
                        txtKeyword2.visibility = View.GONE
                        txtKeyword3.visibility = View.GONE
                    }
                }
                GlideApp.with(holder.itemView).load(it.newsData?.thumb).into(imgNews)
            }
        }
    }

}
