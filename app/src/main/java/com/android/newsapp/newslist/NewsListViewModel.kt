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

    /**
     * 멀티 스레드 코루틴 사용으로 뉴스리스트 파싱
     */
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

                // rss item 리스트에 있는 링크들를 곧바로 html 파싱해야하기 때문에 느려짐 발생
                // TODO : 최대한 빠르게 처리할 수 있도록 백그라운드 관리
                itemList.forEach { item ->
                    job.add(launch(Dispatchers.IO) {
                        item.newsData = htmlTagSearch(item.link!!)
                        Log.d("XXX", "parsing ${item.newsData?.title}")
                    })
                }

                // job 리스트로 먼저 멀티 코루틴을 생성한 후 몰아서 처리
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





