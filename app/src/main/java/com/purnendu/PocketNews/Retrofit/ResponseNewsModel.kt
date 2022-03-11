package com.purnendu.PocketNews.Retrofit

data class ResponseNewsModel(
    val articles: ArrayList<Article>,
    val status: String?,
    val totalResults: Int?
) {
    class Article(
        title: String?,
        description: String?,
        urlToImage: String?,
        url: String?,
        publishedAt: String?

    ) {

        val title: String? = title
            get() = validateData(field)

        val description: String? = description
            get() = validateData(field)


        val url: String? = url
            get() = validateData(field)

        val urlToImage: String? = urlToImage
            get() = validateData(field)

        val publishedAt: String? = publishedAt
            get() = validateData(field)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Article

            if (title != other.title) return false
            if (description != other.description) return false
            if (url != other.url) return false
            if (urlToImage != other.urlToImage) return false
            if (publishedAt != other.publishedAt) return false

            return true
        }

        override fun hashCode(): Int {
            var result = title?.hashCode() ?: 0
            result = 31 * result + (description?.hashCode() ?: 0)
            result = 31 * result + (url?.hashCode() ?: 0)
            result = 31 * result + (urlToImage?.hashCode() ?: 0)
            result = 31 * result + (publishedAt?.hashCode() ?: 0)
            return result
        }

        override fun toString(): String {
            return "Article(title=$title, description=$description, url=$url, urlToImage=$urlToImage, publishedAt=$publishedAt)"
        }


        private fun validateData(element: String?): String {
            if (element == null || element.isEmpty())
                return "null"
            return element
        }


    }
}