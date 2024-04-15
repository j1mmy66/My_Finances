package com.example.test_compose.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test_compose.viewmodel.model.Share
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



class GetSharesService : ViewModel(){

    private val _items = MutableLiveData<List<Share>>()
    val items: LiveData<List<Share>> get() = _items


    fun getShares() {
        try {
            val url = "https://iss.moex.com/iss/engines/stock/markets/shares/boards/TQBR/securities"
            val client = OkHttpClient()
            val secidToShortName = fetchSecidToShortNameMap(url)
            val shares = mutableListOf<Share>()
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.i("www", "fetchSecidToShortNameMap: ")
                    //throw IOException("Unexpected code $response")
                }

                val jsonResponse = response.body?.string()
                val document = getDocumentElement(jsonResponse!!)
                val marketData = getData(document, "marketdata")
                parseMarketData(marketData, secidToShortName, shares)
            }
            val sortedShares = shares.sortedByDescending { it.valToDay }

            CoroutineScope(Dispatchers.IO).launch {

                withContext(Dispatchers.Main) {
                    _items.value = sortedShares
                }
            }
        }
        catch (e : Exception){

        }

    }

    private fun fetchSecidToShortNameMap(url: String): MutableMap<String, String> {
        val client = OkHttpClient()
        val secidToShortName = mutableMapOf<String, String>()
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.i("www", "fetchSecidToShortNameMap: ")
                throw IOException("Unexpected code $response")
            }

            val jsonResponse = response.body?.string()
            val document = getDocumentElement(jsonResponse!!)
            val securitiesData = getData(document, "securities")
            parseSecuritiesData(securitiesData, secidToShortName)
        }
        return secidToShortName
    }

    private fun parseMarketData(
        marketData: Element?,
        secidToShortName: MutableMap<String, String>,
        shares: MutableList<Share>
    ) {
        val rows = marketData?.getElementsByTagName("row")
        if (rows != null) {
            for (i in 0 until rows.length) {
                val row = rows.item(i) as Element
                val secid = row.getAttribute("SECID") ?: ""
                val price = row.getAttribute("LAST") ?: ""
                val openPrice = row.getAttribute("OPEN") ?: ""
                val valToDay = row.getAttribute("VALTODAY") ?: ""
                val shortname = secidToShortName[secid] ?: ""
                if (secid.isNotBlank() && price.isNotBlank() && openPrice.isNotBlank() && valToDay.isNotBlank() && shortname.isNotBlank()) {
                    shares.add(Share(secid, shortname, price.toDouble(), openPrice.toDouble(), valToDay.toLong()))
                }
            }
        }
    }

    private fun parseSecuritiesData(securitiesData: Element?, secidToShortName: MutableMap<String, String>) {
        val rows = securitiesData?.getElementsByTagName("row")
        if (rows != null) {
            for (i in 0 until rows.length) {
                val row = rows.item(i) as Element
                val secid = row.getAttribute("SECID")
                val shortname = row.getAttribute("SHORTNAME")
                secidToShortName[secid] = shortname
            }
        }

    }

    private fun getData(document: Element?, tableName: String): Element? {

        val dataList = document?.getElementsByTagName("data")

        if (dataList != null) {
            for (i in 0 until dataList.length) {
                val data = dataList.item(i) as Element
                if (data.getAttribute("id") == tableName) {
                    return data
                }
            }
        }
        return null
    }

    private fun getDocumentElement(xmlContent: String): Element? {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val inputSource = InputSource(StringReader(xmlContent))
        val document = builder.parse(inputSource)
        return document.documentElement
    }

}