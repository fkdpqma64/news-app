package com.android.newsapp.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.newsapp.R
import com.android.newsapp.databinding.ActivityWebViewBinding
import common.di.injector

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

        supportActionBar?.apply {
            title = intent.getStringExtra(EXTRA_NEWS_TITLE)
            val keyWords = intent.getStringArrayListExtra(EXTRA_NEWS_KEYWORDS)
            if (keyWords != emptyList<String>())
                subtitle = "${keyWords[0]} | ${keyWords[1]} | ${keyWords[2]}"
        }

    }

    private fun setupEvents() {
        mViewModel.webViewSetting(mbind.webView, intent.getStringExtra(EXTRA_NEWS_LINK)!!)

    }
}
