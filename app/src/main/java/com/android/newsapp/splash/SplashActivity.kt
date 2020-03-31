package com.android.newsapp.splash

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.android.newsapp.R
import com.android.newsapp.databinding.ActivitySplashBinding
import common.lib.Activities
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    private lateinit var mBind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        customInit()
        setupEvents()
    }

    private fun customInit() {
        lifecycleScope.launch {
            delay(1300)
            jobMoveHome()
        }
    }

    private fun setupEvents() {
        mBind.imgSplashMain.background = ShapeDrawable(OvalShape())
        mBind.imgSplashMain.clipToOutline = true
    }


    private fun jobMoveHome() {
        Activities.startLaunchActivityFromSplash(thisActivity())
        finish()
    }

    private fun thisActivity(): SplashActivity {
        return this
    }


    override fun finish() {
        overridePendingTransition(0, 0)
        super.finish()
    }
}
