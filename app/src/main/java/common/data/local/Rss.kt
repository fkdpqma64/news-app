package common.data.local

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class Rss {
    @set:Element(name = "channel")
    @get:Element(name = "channel")
    var channel: Channel? = null

}
@Root(name = "channel", strict = false)
class Channel{
    @set:ElementList(entry = "item", inline = true)
    @get:ElementList(entry = "item", inline = true)
    var items: List<Item>? = null
}

@Root(name = "item", strict = false)
class Item{
    @set:Element(name = "title")
    @get:Element(name = "title")
    var title: String? = null

    @set:Element(name = "link")
    @get:Element(name = "link")
    var link: String? = null

    var newsData: NewsData? = null

    var keyWords: ArrayList<String> = arrayListOf()
}