package com.android.newsapp.newslist

import android.content.Context
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import common.api.Api
import common.lib.livedata.CustomViewModel
import common.lib.parse.htmlTagSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListViewModel @Inject constructor(
    private val context: Context,
    private val api: Api
) : CustomViewModel() {

    private val mViewData = MediatorLiveData<List<Any>>()
    val viewData = mViewData.distinctUntilChanged()
    private val job: MutableList<Job> = mutableListOf()

    init {

    }

    fun refreshViewData() {

        if (isDataLoading()) {
            Log.d("XXX", "already DataLoading...")
            return
        }
        viewModelScope.launch(Dispatchers.Default) {
            Log.d("XXX", "${Thread.currentThread()}")
            runDataLoading {
                val itemList = api.call()!!
                Log.d("XXX", "success RSS Parsing")

                itemList.forEach { item ->
                    job.add(launch(Dispatchers.IO) {
                        item.newsData = htmlTagSearch(item.link!!)
                        Log.d("XXX", "parsing ${item.newsData?.title}")
                    })
                }
                job.forEach {
                    it.join()
                }

                Log.d("XXX", "success NEWSLIST Parsing")
                mViewData.postValue(itemList)
                job.forEach {
                    it.cancel()
                }
            }
        }
    }

}





