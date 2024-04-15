package com.example.test_compose.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class GetExchanchgeRateViewModel {

    private val _items = MutableLiveData<List<Double>>()
    val items: LiveData<List<Double>> get() = _items
    fun getExchangeRate() {
        try {
            val url = "https://iss.moex.com/iss/statistics/engines/currency/markets/selt/rates"
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.i("www", "fetchSecidToShortNameMap: ")
                    //throw IOException("Unexpected code $response")
                }
                val jsonResponse = response.body?.string()
                CoroutineScope(Dispatchers.IO).launch {

                    withContext(Dispatchers.Main) {
                        _items.value = extractPricesFromXML(jsonResponse!!)
                    }
                }

            }
        }
        catch (e : Exception) {

        }
    }

    private fun extractPricesFromXML(xmlString: String): List<Double> {
        val prices = mutableListOf<Double>()
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
            InputSource(
                StringReader(xmlString)
            )
        )
        val rows = document.getElementsByTagName("row")
        for (i in 0 until rows.length) {
            val row = rows.item(i) as Element
            val price = row.getAttribute("price").toDoubleOrNull()
            if (price != null) {
                prices.add(price)
            }
        }
        return prices
    }
}