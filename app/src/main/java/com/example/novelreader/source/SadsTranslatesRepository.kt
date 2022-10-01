package com.example.novelreader.source

import android.util.Log
import com.example.novelreader.ApiService
import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class SadsTranslatesRepository : RepositoryInterface {

    private val baseUrl = "https://sads07.wordpress.com"

    private val apiService: ApiService = ApiService.getInstance(baseUrl)

    override val id: Int
        get() = 1

    override val name: String
        get() = "Sads Translates"

    override suspend fun getNovelList(): List<Novel> {
        val response = apiService.getNovelList()

        val jsoup: Document = Jsoup.parse(response)

        val links = jsoup
            .select(
                ".entry-content>ul:nth-of-type(1)>li>a, " +
                        ".entry-content>ul:nth-of-type(2)>li>a"
            )

        val list = mutableListOf<Novel>()

        for ((i, el) in links.withIndex()) {
            val n = Novel(i, el.text(), el.attr("href"))
            list.add(getCover(n))
        }

        return list
    }

    override suspend fun getNovelDetails(novel: Novel): Novel {
        TODO("Not yet implemented")
    }

    override suspend fun getChapters(novel: Novel): List<Chapter> {
        TODO("Not yet implemented")
    }

    override suspend fun getCover(novel: Novel): Novel {
        val response = apiService.getFromUrl(novel.url.replace("$baseUrl/", ""))
        val jsoup: Document = Jsoup.parse(response)

        val url = jsoup
            .select("figure.size-large>img")
            .attr("src")

        return novel.copy(cover = url)
    }
}