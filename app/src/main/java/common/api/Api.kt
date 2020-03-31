package common.api

import android.content.Context
import common.data.local.Item
import javax.inject.Singleton

@Singleton
class Api(val context: Context, private val serverApi: ServerApi) {

    /**
     * Retrofit - SimpleXMLParser 사용으로 데이터 파싱 -> item 리스트 반환
     */
    fun call(): List<Item>? {
        val call = serverApi.getData()
        return call?.execute()?.body()!!.channel!!.items
    }
}
