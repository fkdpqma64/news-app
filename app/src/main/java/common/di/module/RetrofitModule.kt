package common.di.module

import android.content.Context
import common.api.Api
import common.api.ServerApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Suppress("DEPRECATION")
    @JvmStatic
    @Provides
    @Singleton
    fun provideServerApi(): ServerApi = Retrofit.Builder()
        .baseUrl("https://news.google.com/")
        .client(OkHttpClient())
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()
        .create(ServerApi::class.java)

    @JvmStatic
    @Provides
    @Singleton
    fun provideApi(context: Context, serverApi: ServerApi): Api {
        return Api(context, serverApi)
    }
}