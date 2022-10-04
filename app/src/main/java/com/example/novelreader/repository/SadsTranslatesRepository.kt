package com.example.novelreader.repository

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.novelreader.ApiService
import com.example.novelreader.HtmlConverter
import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel
import com.example.novelreader.model.Paragraph
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
            val url = el.attr("href")
            val n = Novel(i, el.text(), url)
            n.coverUrl = getCover(n.url)
            list.add(n)
        }

        return list
    }

    override suspend fun getNewNovelList(): List<Novel> {
        val jsoup: Document = Jsoup.parse(apiService.getFromUrl(latestChaptersPath))
        val headers = jsoup.select(".entry-title>a")

        var titleUrls = headers.map {
            getProjectUrlFromChapterUrl(it.attr("href"))
        }

        titleUrls = titleUrls
            .toSet()
            .filter { it != "" }
            .toList()
            .map { it }

        val novels = getAllNovelList()

        val result = mutableListOf<Novel>()

        for (u in titleUrls) {
            for (n in novels) {
                if (u == n.url) {
                    result.add(n)
                    break
                }
            }
        }

        return result
    }

    override suspend fun getCover(novelUrl: String): String {
        val response = apiService.getFromUrl(novelUrl.replace(baseUrl, ""))
        val jsoup: Document = Jsoup.parse(response)

        return jsoup
            .select("figure.size-large>img")
            .attr("src")
    }

    override suspend fun getNovelDetails(novelUrl: String): Novel {
        val jsoup: Document = Jsoup.parse(apiService.getFromUrl(novelUrl.replace(baseUrl, "")))

        // TODO get full title

        return Novel(
            id = 1,
            title = jsoup.select(".entry-title").text(),
            url = novelUrl,
            chapterList = getChaptersFromHtml(jsoup),
            coverUrl = jsoup.select("figure.size-large>img").attr("src"),
            description = getDescriptionFromHtml(jsoup)
        )
    }

    override suspend fun getChapters(novelUrl: String): List<Chapter> {
        TODO("Not yet implemented")
    }

    private suspend fun getProjectUrlFromChapterUrl(chapterFullUrl: String): String {

        val jsoup: Document = Jsoup.parse(
            apiService.getFromUrl(chapterFullUrl.replace(baseUrl, ""))
        )

        val links = jsoup.select("div.entry-content>p>a:nth-of-type(1)").first()

        return links?.attr("href").toString()
    }

    private fun getDescriptionFromHtml(jsoup: Document): MutableList<Paragraph> {
        // TODO test
        val list = mutableListOf<Paragraph>()

        val content = jsoup.select(".entry-content").first()

        content!!.children().forEachIndexed { i, p ->
            list.add(Paragraph(i, p.html(), p, HtmlConverter.paragraphToAnnotatedString(p)))
        }

        return list
    }

    private fun getChaptersFromHtml(jsoup: Document): MutableList<Chapter> {

        val list = mutableStateListOf<Chapter>()
        val elements = jsoup.select("details>p>a, .entry-content > p > a")

        elements.forEach { el ->
            if (el.attr("href").contains(baseUrl)) {
                list.add(Chapter(el.text(), el.attr("href").replace(baseUrl, "")))
            }
        }

        return list
    }
}