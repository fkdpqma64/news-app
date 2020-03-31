package common.api

import common.data.local.Rss
import retrofit2.Call
import retrofit2.http.GET


interface ServerApi {
    @GET("rss?hl=ko&gl=KR&ceid=KR:ko")
    fun getData(): Call<Rss?>?
}