package com.example.novelreader.repository

import android.util.Log
import com.example.novelreader.ApiService
import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class SadsTranslatesRepository : RepositoryInterface {

    private val baseUrl = "https://sads07.wordpress.com"

    private val projectListPath = "/projects/"
    private val latestChaptersPath = "/"

    private val apiService: ApiService = ApiService.getInstance(baseUrl)

    override val id: Int
        get() = 1

    override val name: String
        get() = "Sads Translates"

    override suspend fun getAllNovelList(): List<Novel> {
        val jsoup: Document = Jsoup.parse(apiService.getFromUrl(projectListPath))

        val links = jsoup
            .select(
                ".entry-content>ul:nth-of-type(1)>li>a, " +
                        ".entry-content>ul:nth-of-type(2)>li>a"
            )

        val list = mutableListOf<Novel>()

        for ((i, el) in links.withIndex()) {
            val url = el.attr("href").replace(baseUrl, "")
            val n = Novel(i, el.text(), url)
            n.coverUrl = getCover(n)
            list.add(n)
        }

        return list
    }

    override suspend fun getNewNovelList(): List<Novel> {
        val jsoup: Document = Jsoup.parse(apiService.getFromUrl(latestChaptersPath))

        val headers = jsoup.select(".entry-title>a")

        val chapterUrl = headers.map {
            it.attr("href")
        }

        val titleUrl = chapterUrl.map {
            getTitleFromChapter(it)
        }

        Log.d("myk", titleUrl.toString())

        val list = mutableListOf<Novel>()

        return list
    }

    override suspend fun getNovelDetails(novel: Novel): Novel {
        TODO("Not yet implemented")
    }

    override suspend fun getChapters(novel: Novel): List<Chapter> {
        TODO("Not yet implemented")
    }

    override suspend fun getCover(novel: Novel): String {
        return getCover(novel.url)
    }

    suspend fun getCover(novelUrl: String): String {
        val response = apiService.getFromUrl(novelUrl)
        val jsoup: Document = Jsoup.parse(response)

        return jsoup
            .select("figure.size-large>img")
            .attr("src")
    }

    private suspend fun getTitleFromChapter(chapterFullUrl: String) {
        val jsoup: Document = Jsoup.parse(
            apiService.getFromUrl(chapterFullUrl.replace(baseUrl, ""))
        )

        val headers = jsoup.select(".entry-title>a")

    }
}