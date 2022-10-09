package com.example.novelreader.source

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.novelreader.ApiService
import com.example.novelreader.HtmlConverter
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel
import com.example.novelreader.database.model.Paragraph
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class SadsTranslatesSource : SourceInterface {

    private val projectListPath = "/projects/"
    private val latestChaptersPath = "/"

    private val apiService: ApiService = ApiService.getInstance(baseUrl)

    override val id: Int
        get() = 1

    override val name: String
        get() = "Sads Translates"

    override val baseUrl: String
        get() = "https://sads07.wordpress.com"

    override suspend fun getAllNovelList(): List<Novel> {
        val jsoup: Document = Jsoup.parse(apiService.getFromUrl(projectListPath))

        val links = jsoup
            .select(
                ".entry-content>ul:nth-of-type(1)>li>a, " +
                        ".entry-content>ul:nth-of-type(2)>li>a"
            )

        val list = mutableListOf<Novel>()

        for (el in links) {
            val url = el.attr("href")
            val n = Novel(title = el.text(), url = url, inDatabase = false, sourceId = id)
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
        return Novel(
            title = jsoup.select(".entry-title").text(),
            url = novelUrl,
            chapterList = getChaptersFromHtml(jsoup),
            coverUrl = jsoup.select("figure.size-large>img").attr("src"),
            description = getDescriptionFromHtml(jsoup)
        )
    }

    override suspend fun getChapters(novelUrl: String): List<Chapter> {
        return getChaptersFromHtml(
            Jsoup.parse(
                apiService.getFromUrl(
                    novelUrl.replace(
                        baseUrl,
                        ""
                    )
                )
            )
        )
    }

    override suspend fun getChapterContent(chapterUrl: String): Chapter {
        // change url
        val response = apiService.getFromUrl(chapterUrl.replace(baseUrl, ""))

        val jsoup: Document = Jsoup.parse(response)

        val t = jsoup.select(".entry-title")
            .first()
            ?.html()
            ?.replace("&nbsp;", " ")

        val content = jsoup.select(".entry-content")
            .first()

        val title = t.toString()

        val list = parseParagraphs(content.toString())

        return Chapter(
            title = title,
            url = chapterUrl,
            content = list,
            number = 1
        )
    }

    private suspend fun getProjectUrlFromChapterUrl(chapterFullUrl: String): String {

        val jsoup: Document = Jsoup.parse(
            apiService.getFromUrl(chapterFullUrl.replace(baseUrl, ""))
        )

        val links = jsoup.select("div.entry-content>p>a:nth-of-type(2)").first()

        return links?.attr("href").toString()
    }

    private fun getDescriptionFromHtml(jsoup: Document): MutableList<Paragraph> {
        val list = mutableListOf<Paragraph>()

        val content = jsoup.select(".entry-content>p.has-drop-cap").first() ?: return list

        for ((i, el) in content.childNodes().withIndex()) {
            if (el is TextNode) {
                list.add(
                    Paragraph(
                        number = i,
                        html = el.text(),
                        annotatedString = AnnotatedString(el.text())
                    )
                )
            } else if (el is Element) {
                list.add(
                    Paragraph(
                        number = i,
                        html = el.text(),
                        annotatedString = HtmlConverter.paragraphToAnnotatedString(el)
                    )
                )
            }
        }

        return list
    }

    private fun getChaptersFromHtml(jsoup: Document): MutableList<Chapter> {

        val list = mutableStateListOf<Chapter>()
        val elements = jsoup.select("details>p>a, .entry-content > p > a")

        elements.forEachIndexed { index, el ->
            if (el.attr("href").contains(baseUrl)) {
                list.add(Chapter(title = el.text(), url = el.attr("href"), number = index))
            }
        }

        return list
    }

    private fun parseParagraphs(html: String): MutableList<Paragraph> {
        val list = mutableStateListOf<Paragraph>()
        val jsoup = Jsoup.parse(html)

        val title = jsoup.select("h2").first()
        if (title != null)
            list.add(
                Paragraph(
                    id = 0,
                    number = 0,
                    html = title.html(),
                    annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 27.sp)) {
                            append(HtmlConverter.paragraphToAnnotatedString(title))
                        }
                    }
                )
            )

        for ((i, p) in jsoup.select("p").withIndex()) {

            if (i == 0) continue

            list.add(
                Paragraph(
                    id = 0,
                    number = i + 1,
                    html = p.html(),
                    annotatedString = HtmlConverter.paragraphToAnnotatedString(p)
                )
            )
        }

        return list
    }
}