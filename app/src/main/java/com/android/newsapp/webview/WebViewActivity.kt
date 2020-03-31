package com.android.newsapp.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.newsapp.R
import com.android.newsapp.databinding.ActivityWebViewBinding
import common.di.injector

class WebViewActivity : AppCompatActivity() {

    lateinit var mbind: ActivityWebViewBinding
    val mViewModel by lazy { injector.webViewActivityViewModel }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
    }
}
