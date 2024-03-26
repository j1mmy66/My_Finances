package com.example.test_compose.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import java.time.LocalDate
import javax.xml.parsers.DocumentBuilderFactory

class GetShareQuotesService : ViewModel(){

    @RequiresApi(Build.VERSION_CODES.O)
    fun getShareQuotes(secid: String): List<Double> {
        val quotes = mutableListOf<Double>()
        var dateFrom = LocalDate.now().minusYears(1)
        var dateTo = LocalDate.now().minusYears(1).plusMonths(4)
        for (j in 0..2) {
            val url =
                "https://iss.moex.com/iss/history/engines/stock/markets/shares/securities/${secid}?from=${dateFrom}&till=${dateTo}&marketprice_board=1"
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("Unexpected code $response")
                }
                val jsonResponse = response.body?.string()
                val docFactory = DocumentBuilderFactory.newInstance()
                val docBuilder = docFactory.newDocumentBuilder()
                val inputSource = InputSource(StringReader(jsonResponse!!))
                val doc = docBuilder.parse(inputSource)

                val rows = doc.getElementsByTagName("row")
                for (i in 0 until rows.length) {
                    val row = rows.item(i) as Element
                    val wapPrice = row.getAttribute("WAPRICE")
                    if (!wapPrice.isNullOrBlank()) {
                        quotes.add(wapPrice.toDouble())
                    }
                }
            }
            dateFrom = dateFrom.plusMonths(4)
            dateTo = dateTo.plusMonths(4)
        }
        return quotes
    }
}