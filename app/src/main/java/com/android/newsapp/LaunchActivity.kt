package com.android.newsapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.newsapp.databinding.ActivityLaunchBinding
import com.android.newsapp.newslist.NewsListFragment
import com.android.newsapp.splash.SplashActivity
import common.data.local.Item
import common.di.injector
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {
    companion object {

        const val EXTRA_LAUNCH_FROM_SPLASH = "EXTRA_LAUNCH_FROM_SPLASH"
        const val EXTRA_NEWS_TITLE = "EXTRA_NEWS_TITLE"
        const val EXTRA_NEWS_LINK = "EXTRA_NEWS_LINK"
        const val EXTRA_NEWS_KEYWORDS = "EXTRA_NEWS_KEYWORDS"

        fun createIntent(context: Context) = Intent(context, LaunchActivity::class.java)

        fun createIntentFromSplash(context: Context) =
            Intent(context, LaunchActivity::class.java).also {
                it.putExtra(EXTRA_LAUNCH_FROM_SPLASH, true)
            }
    }

    private var mSplashVisited = false
    private lateinit var mBind: ActivityLaunchBinding

    private val mViewModel by lazy { injector.launchActivityViewModel }
    private val mApp by lazy { injector.app }

    override fun onCreate(savedInstanceState: Bundle?) {
        val launchFromLink = mApp.backupMainIntent(intent)

        if (!launchFromLink && checkNeedSplashVisit(mSplashVisited)) {
            super.onCreate(savedInstanceState)
            finish()
            startActivity(SplashActivity.createIntent(this))
            return
        }

        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_launch)
        customInit()
        setupEvents()

    }

    private fun customInit() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.news_list_fragment, NewsListFragment.newInstance()).commit()
    }

    private fun checkNeedSplashVisit(mSplashVisited: Boolean): Boolean {
        if (mSplashVisited)
            return false

        val fromSplash = intent?.getBooleanExtra(EXTRA_LAUNCH_FROM_SPLASH, false) == true
        return !fromSplash
    }

    private fun setupEvents() {

    }
}
