package common.api

import android.content.Context
import common.data.local.Item
import javax.inject.Singleton

@Singleton
class Api(val context: Context, private val serverApi: ServerApi) {

    fun call(): List<Item>? {
        val call = serverApi.getData()
        return call?.execute()?.body()!!.channel!!.items
    }
}
