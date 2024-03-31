package com.example.test_compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_compose.viewmodel.model.News
import com.example.test_compose.viewmodel.model.Share
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory

class GetNewsViewModel : ViewModel(){

    private val _items = MutableLiveData<List<News>>()
    val items: LiveData<List<News>> get() = _items
    fun getNews(){
        val url = "https://iss.moex.com/iss/sitenews"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        val news = mutableListOf<News>()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            val jsonResponse = response.body?.string()

            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                InputSource(jsonResponse!!.reader())
            )
            val rows = document.getElementsByTagName("row")

            for (i in 0 until rows.length) {
                val row = rows.item(i) as Element
                val id = row.getAttribute("id")
                val title = row.getAttribute("title")
                news.add(News(id, Jsoup.parseBodyFragment(title).body().text()))
            }
        }
        CoroutineScope(Dispatchers.IO).launch {

            withContext(Dispatchers.Main) {
                _items.value = news
            }
        }
    }

    fun getNewsById(id: String): String {
        val url = "https://iss.moex.com/iss/sitenews/${id}"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()


        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            val jsonResponse = response.body?.string()

            val doc = Jsoup.parse(jsonResponse!!)
            val bodyContent = doc.select("row").attr("body")
            return Jsoup.parseBodyFragment(bodyContent).body().text()
        }

    }

}