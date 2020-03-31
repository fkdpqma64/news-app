package com.android.newsapp.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.newsapp.R
import com.android.newsapp.databinding.ActivityWebViewBinding
import common.di.injector

/**
 * 웹 뷰 액티비티
 */
class WebViewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NEWS_TITLE = "EXTRA_NEWS_TITLE"
        const val EXTRA_NEWS_LINK = "EXTRA_NEWS_LINK"
        const val EXTRA_NEWS_KEYWORDS = "EXTRA_NEWS_KEYWORDS"
    }

    lateinit var mbind: ActivityWebViewBinding
    val mViewModel by lazy { injector.webViewActivityViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbind = DataBindingUtil.setContentView(this, R.layout.activity_web_view)

        customInit()
        setupEvents()
    }

    private fun customInit() {
        /**
         * 뉴스 제목과 키워드를 가져와서 액션바 타이틀과 서브타이틀에 보여줌
         */
        supportActionBar?.apply {
            title = intent.getStringExtra(EXTRA_NEWS_TITLE)
            val keyWords = intent.getStringArrayListExtra(EXTRA_NEWS_KEYWORDS)
            if (keyWords != emptyList<String>())
                subtitle = "${keyWords[0]} | ${keyWords[1]} | ${keyWords[2]}"
        }

    }

    private fun setupEvents() {
        /**
         * 웹 뷰 셋팅
         */
        mViewModel.webViewSetting(mbind.webView, intent.getStringExtra(EXTRA_NEWS_LINK)!!)

    }
}
