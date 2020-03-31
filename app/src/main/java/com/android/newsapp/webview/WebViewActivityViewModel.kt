package com.android.newsapp.webview

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged
import common.lib.livedata.CustomViewModel
import javax.inject.Inject


class WebViewActivityViewModel @Inject constructor(
    private val context: Context
) : CustomViewModel() {

    private val mViewData = MediatorLiveData<List<Any>>()
    val viewData = mViewData.distinctUntilChanged()

    init {

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun webViewSetting(webView: WebView, url: String) {
        webView.webViewClient = WebViewClient()
        val setting = webView.settings
        setting.javaScriptEnabled = true
        setting.setSupportMultipleWindows(false)
        setting.javaScriptCanOpenWindowsAutomatically = false
        setting.loadWithOverviewMode = true
        setting.useWideViewPort = true
        setting.setSupportZoom(true)
        setting.builtInZoomControls = false
        setting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        setting.cacheMode = WebSettings.LOAD_NO_CACHE
        setting.domStorageEnabled = true

        webView.loadUrl(url)
    }
}