package common.data.local

/**
 * 뉴스 데이터 VO
 */
data class NewsData(
    var title: String = "",
    var url: String = "",
    var thumb: String = "",
    var description: String = "",
    var article: String? = null
)