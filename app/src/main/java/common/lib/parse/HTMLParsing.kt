package common.lib.parse

import android.util.Log
import common.data.local.NewsData
import common.lib.ssl.SSLConnect
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * html 태그 검색
 */
fun htmlTagSearch(url: String): NewsData {
    val item = NewsData()

    try {
        val ssl = SSLConnect()
        ssl.postHttps(url, 1000, 1000)
        val doc: Document = Jsoup.connect(url).get()
        val ogTags: Elements = doc.select("meta[property^=og:]")
        val article: Elements = doc.select("div[itemprop^=articleBody]")

        // 필요한 OGTag를 추려낸다
        ogTags.forEach {
            Log.d("XXX", Thread.currentThread().name)
            val tag: Element = it
            when (tag.attr("property")) {
                "og:title" -> {
                    item.title = tag.attr("content")
                }
                "og:image" -> {
                    item.thumb = tag.attr("content")
                }
                "og:url" -> {
                    item.url = tag.attr("content")
                }
                "og:description" -> {
                    item.description = tag.attr("content")
                }
            }
        }

        // 본문 추가 - 뉴스 본문에서 가장 많이 사용하는 태그 대표로 가져옴
        article.forEach {
            val tag: Element = it
            when (tag.attr("itemprop")) {
                "articleBody" -> {
                    item.article = tag.text()
                }
            }
        }

    } catch (e: Exception) {
        Log.d("ERROR", e.message!!)
    }

    return item

}

/**
 * 특수 문자 제거
 */
fun strReplace(str: String): String {
    val match = """[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]""".toRegex()
    return str.replace(match, " ")
}

/**
 * 뉴스 단어 빈도수 측정
 */
fun strWordCount(list: List<String>): HashMap<String, Int> {
    val wordCount: HashMap<String, Int> = hashMapOf()
    list.forEach {
        val str = it.trim()
        if (!wordCount.containsKey(str)) {
            wordCount[str] = 1
        } else {
            wordCount[str] = wordCount[str]!! + 1
        }
    }
    return wordCount
}

/**
 * 뉴스 키워드 리스트
 */
fun keyWordsList(desc: String): List<String> {
    val keyWords: List<String> = strReplace(desc).split(" ")

    return strWordCount(keyWords).filterKeys { it.length > 1 }.toSortedMap().toList()
        .sortedByDescending { (_, value) -> value }.toMap().keys.toList()
}