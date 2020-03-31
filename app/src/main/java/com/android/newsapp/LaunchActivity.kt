package com.android.newsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.newsapp.databinding.ActivityLaunchBinding
import com.android.newsapp.newslist.NewsListFragment
import com.android.newsapp.splash.SplashActivity
import common.di.injector

class LaunchActivity : AppCompatActivity() {
    companion object {

        const val EXTRA_LAUNCH_FROM_SPLASH = "EXTRA_LAUNCH_FROM_SPLASH"

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

        /**
         * 스플래쉬 화면 실행
         */
        if (!launchFromLink && checkNeedSplashVisit(mSplashVisited)) {
            super.onCreate(savedInstanceState)
            finish()
            startActivity(SplashActivity.createIntent(this).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
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

    /**
     * 스플래쉬 화면 방문 체크
     */
    private fun checkNeedSplashVisit(mSplashVisited: Boolean): Boolean {
        if (mSplashVisited)
            return false

        val fromSplash = intent?.getBooleanExtra(EXTRA_LAUNCH_FROM_SPLASH, false) == true
        return !fromSplash
    }

    private fun setupEvents() {

    }
}
