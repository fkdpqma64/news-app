package common.di

import android.app.Application
import android.content.Context
import com.android.newsapp.LaunchActivityViewModel
import com.android.newsapp.webview.WebViewActivityViewModel
import common.App
import common.di.module.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class
    ]
)

interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application, @BindsInstance applicationContext: Context): AppComponent
    }

    val app: App

    val launchActivityViewModel: LaunchActivityViewModel
    val webViewActivityViewModel: WebViewActivityViewModel

}