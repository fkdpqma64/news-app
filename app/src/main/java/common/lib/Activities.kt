package common.lib

import android.content.Context
import android.content.Intent
import com.android.newsapp.LaunchActivity

object Activities {
    private fun startLaunchActivity(context: Context) {
        val intent = LaunchActivity.createIntent(context).also {
            it.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        or Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        }

        context.startActivity(intent)
    }

    fun startLaunchActivityFromSplash(context: Context) {
        val intent = LaunchActivity.createIntentFromSplash(context).also {
            it.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        or Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        }

        context.startActivity(intent)
    }

}
