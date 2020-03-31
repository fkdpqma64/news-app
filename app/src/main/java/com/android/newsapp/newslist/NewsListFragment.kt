package com.android.newsapp.newslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.newsapp.LaunchActivity
import com.android.newsapp.R
import com.android.newsapp.databinding.FragmentNewsListBinding
import com.android.newsapp.webview.WebViewActivity
import common.data.local.Item
import common.di.injector
import kotlinx.coroutines.launch

/**
 * 뉴스 리스트 프래그먼트
 */
class NewsListFragment : Fragment() {

    companion object {
        const val EXTRA_NEWS_TITLE = "EXTRA_NEWS_TITLE"
        const val EXTRA_NEWS_LINK = "EXTRA_NEWS_LINK"
        const val EXTRA_NEWS_KEYWORDS = "EXTRA_NEWS_KEYWORDS"

        fun newInstance() = NewsListFragment()
    }

    private lateinit var mBind: FragmentNewsListBinding
    private lateinit var mAdapter: NewsListAdapter
    private val mViewModel by lazy { requireActivity().injector.newsListViewModel }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBind = DataBindingUtil.inflate(inflater, R.layout.fragment_news_list, container, false)
        mBind.lifecycleOwner = this
        return mBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customInit()
        setupEvents()
        setupRecyclerView()
    }

    private fun customInit() {
        mViewModel.refreshViewData()
    }

    private fun setupEvents() {

        /**
         * 뷰모델에서 가져온 데이터를 Observe하여 RecyclerView에 등록
         */
        mViewModel.viewData.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                mBind.progressBar.visibility = View.VISIBLE
            }
            lifecycleScope.launch {
                mAdapter.items = it as List<Item>
                mBind.progressBar.visibility = View.GONE
                mAdapter.notifyDataSetChanged()
                mBind.newsList.adapter = mAdapter
            }
        })

        /**
         * 새로고침 리스너
         */
        mBind.swipeLayout.setOnRefreshListener {
            mViewModel.refreshViewData()
        }

        /**
         * 데이터 로딩 상태 Observe하여 스와이프 레이어 관리
         */
        mViewModel.dataLoading.observe(viewLifecycleOwner, Observer { yes ->
            if (!yes) {
                mBind.swipeLayout.isRefreshing = false
            }
        })

    }

    /**
     * RecyclerView 셋업
     */
    private fun setupRecyclerView() {
        mBind.newsList.apply {
            layoutManager = LinearLayoutManager(thisActivity())
            addItemDecoration(
                DividerItemDecoration(
                    thisActivity(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        mAdapter = NewsListAdapter()

        /**
         * 클릭 리스너 - 클릭시 해당 뉴스가 담긴 웹뷰 액티비티로 전환
         */
        mAdapter.clickListener = { item ->
            Toast.makeText(thisActivity(), item.title, Toast.LENGTH_SHORT).show()
            val intent = Intent(thisActivity(), WebViewActivity::class.java).also {
                it.putExtra(EXTRA_NEWS_TITLE, item.title)
                it.putExtra(EXTRA_NEWS_LINK, item.link)
                it.putExtra(EXTRA_NEWS_KEYWORDS, item.keyWords)
            }
            startActivity(intent)
        }

    }

    private fun thisActivity(): LaunchActivity? {
        return activity as? LaunchActivity
    }
}
